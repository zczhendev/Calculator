package io.zczhendev.calculator.utils.calculate

import io.zczhendev.calculator.utils.calculate.exception.UnsupportedOperatorException
import io.zczhendev.calculator.utils.calculate.key.Key
import io.zczhendev.calculator.utils.calculate.key.NumberKey
import io.zczhendev.calculator.utils.calculate.key.OperatorKey
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.LinkedBlockingQueue
import kotlin.math.pow

/**
 * 计算器核心类，所有计算操作均在此类中实现
 */
class Calculator(callback: Callback) {

    private val keyQueue: BlockingQueue<Key> = LinkedBlockingQueue()

    private val calculateThread = CalculateThread(keyQueue, callback)

    init {
        calculateThread.start()
    }

    /**
     * 按下一个按键
     */
    fun keyDown(key: Key) {
        keyQueue.put(key)
    }

    /**
     * 清除全部
     */
    fun allClear() {
        keyQueue.clear()
        calculateThread.clear()
    }

    fun close() {
        if (calculateThread.isAlive && !calculateThread.isInterrupted) {
            calculateThread.interrupt()
        }
    }

    private class CalculateThread(
        private val keyQueue: BlockingQueue<Key>,
        private val callback: Callback
    ) : Thread() {

        /**
         * 数字按键缓冲区池
         */
        private val numberKeyBuffers = mutableListOf<MutableList<Byte>>(mutableListOf())

        /**
         * 数字按键缓冲区
         */
        private val numberKeyBuffer: MutableList<Byte>
            get() = numberKeyBuffers.last()

        private val queue: LinkedList<QueueNode> = LinkedList()

        init {
            isDaemon = true
        }

        /**
         * 数字长度溢出
         */
        private var isOverlongNumber = false

        override fun run() {
            while (!isInterrupted) {
                when (val key = keyQueue.take()) {
                    is NumberKey -> {
                        inputNumber(key)
                    }
                    is OperatorKey -> {
                        readNumber()
                        if (key != OperatorKey.KEY_CALCULATE) {
                            inputOperator(key)
                        } else {
                            doCalculate()
                        }
                    }
                }
            }
        }

        private fun doCalculate() {
            val q = queue.clone() as LinkedList<QueueNode>
            val result = calculation(queue)
            callback.onCalculate(q, result)
        }

        /**
         * 计算队列的算式
         */
        private fun calculation(queue: LinkedList<QueueNode>): Double {
            //构建二叉树
            //1、数字与符号均作为节点，数字只能是叶子节点且叶子节点也之只能是数字（数字节点的优先级最高）
            //2、符号节点的优先级按照算术规则定义
            //3、优先级越高所在层级也越高（实现算式运算的关键）
            //4、插入符号是需要找到优先级比其高或相等的节点将其替换并将该节点作为插入节点的左子节点
            //5、插入节点始终是在右子树插入的
            //6、遇括号时将其内容算出结果后作为数字节点插入
            var rootNote: TreeNode? = null
            while (!queue.isEmpty()) {
                // 根据队列节点创建二叉树节点
                val queueNode = queue.poll()
                val node = when (queueNode) {
                    is NumberQueueNode -> {
                        NumberTreeNode(queueNode.number)
                    }
                    is OperatorQueueNode -> {
                        if (queueNode.operatorKey == OperatorKey.KEY_PARENTHESES_LEFT) {
                            // 当遇到括号时先计算其结果
                            val newQueue = LinkedList<QueueNode>()
                            while (!queue.isEmpty()) {
                                val node = queue.poll()
                                if (node is OperatorQueueNode && node.operatorKey == OperatorKey.KEY_PARENTHESES_RIGHT) {
                                    break
                                }
                                newQueue.offer(node)
                            }
                            NumberTreeNode(calculation(newQueue))
                        } else {
                            OperatorTreeNode(Operator.getByKey(queueNode.operatorKey))
                        }
                    }
                    else -> {
                        null
                    }
                } ?: continue   // 如果节点为空则跳过
                if (rootNote == null) { // 若根节点为空则设置位根节点
                    rootNote = node
                    continue
                }
                // 根节点不为空时插入
                // 临时节点的父节点
                var parent: TreeNode? = null
                // 临时节点
                var temp = rootNote
                while (temp != null) {
                    if (temp.priority >= node.priority) {
                        // 当临时节点的优先级大于等于新节点时
                        // 替换临时位置且将该节点置为新节点的左子节点
                        if (parent != null) {
                            parent.rightNode = node
                        }
                        if (temp == rootNote) {
                            rootNote = node
                        }
                        node.leftNode = temp
                        break
                    } else {
                        // 当临时的优先级小于新节点时
                        // 判断其是否拥有右子节点
                        // 若无则设置为右子节点
                        // 若有则继续遍历
                        if (temp.rightNode == null) {
                            temp.rightNode = node
                            break
                        } else {
                            parent = temp
                            temp = temp.rightNode
                        }
                    }
                }
            }

            //算结果，深度优先
            rootNote?.let {
                return calculation(rootNote)
            }

            return 0.0
        }

        fun calculation(node: TreeNode?): Double {
            if (node != null) {
                val a = calculation(node.leftNode)
                val b = calculation(node.rightNode)

                return if (node is NumberTreeNode) {
                    node.number
                } else {
                    when ((node as OperatorTreeNode).operator) {
                        Operator.ADD -> a + b
                        Operator.SUB -> a - b
                        Operator.MULT -> a * b
                        Operator.DIV -> a / b
                    }
                }
            }
            return 0.0
        }

        private fun inputOperator(key: OperatorKey) {
            // 操作符入队
            queue.offer(OperatorQueueNode(key))
        }

        private fun readNumber() {
            if (numberKeyBuffer.isEmpty()) {
                return
            }
            // 读取缓冲区的数字并新建一个缓冲区
            var num = 0.0
            var index = 0
            // 倒叙 第一位*1 第二位*10 第三位*100 以此类推
            for (i in numberKeyBuffer.reversed()) {
                num += i * 10.0.pow(index++.toDouble()).toInt()
            }
            numberKeyBuffers.add(mutableListOf())
            queue.offer(NumberQueueNode(num))
        }

        private fun inputNumber(key: Key) {
            // 最大支持9位数字运算
            if (numberKeyBuffer.size < 9) {
                numberKeyBuffer.add((key as NumberKey).number)
            } else {
                isOverlongNumber = true
            }
        }

        fun clear() {
            numberKeyBuffers.clear()
            numberKeyBuffers.add(mutableListOf())
        }

        /**
         * 定义与优先级
         */
        private enum class Operator(val operator: String, val priority: Int) {
            ADD("+", 1),
            SUB("-", 1),
            MULT("×", 2),
            DIV("÷", 2);

            companion object {
                fun getByKey(key: OperatorKey): Operator {
                    for (operator in values()) {
                        if (key.operator == operator.operator) {
                            return operator
                        }
                    }
                    throw UnsupportedOperatorException(key.operator)
                }
            }
        }

        /**
         * 定义二叉树节点
         */
        private abstract class TreeNode(val priority: Int) {
            var leftNode: TreeNode? = null
            var rightNode: TreeNode? = null
        }

        private class OperatorTreeNode(val operator: Operator) : TreeNode(operator.priority) {
            override fun toString(): String {
                return operator.operator
            }
        }

        private class NumberTreeNode(val number: Double) : TreeNode(10) {
            override fun toString(): String {
                return number.toString()
            }
        }
    }

    /**
     * 定义队列的节点
     */
    abstract class QueueNode

    class OperatorQueueNode(val operatorKey: OperatorKey) : QueueNode() {
        override fun toString(): String {
            return operatorKey.operator
        }
    }

    class NumberQueueNode(val number: Double) : QueueNode() {
        override fun toString(): String {
            return number.toString()
        }
    }

    interface Callback {
        fun onCalculate(queue: LinkedList<QueueNode>, result: Double)
    }

}

fun Calculator(callback: (queue: LinkedList<Calculator.QueueNode>, result: Double) -> Unit): Calculator {
    return Calculator(object : Calculator.Callback {
        override fun onCalculate(
            queue: LinkedList<Calculator.QueueNode>,
            result: Double
        ) {
            callback(queue, result)
        }

    })
}