package io.zczhendev.calculator.utils.calculate

import io.zczhendev.calculator.utils.calculate.key.NumberKey
import io.zczhendev.calculator.utils.calculate.key.OperatorKey
import org.junit.Test

internal class CalculatorTest {
    @Test
    fun test() {
        val calculator = Calculator { queue, result ->
            queue.forEach {
                print(it)
            }
            println("=$result")
        }
        calculator.apply {
            keyDown(NumberKey.KEY_1)
            keyDown(NumberKey.KEY_0)

            keyDown(OperatorKey.KEY_MULT)

            keyDown(OperatorKey.KEY_PARENTHESES_LEFT)
            keyDown(NumberKey.KEY_2)
            keyDown(OperatorKey.KEY_ADD)
            keyDown(NumberKey.KEY_1)
            keyDown(OperatorKey.KEY_PARENTHESES_RIGHT)
            keyDown(OperatorKey.KEY_CALCULATE)
        }

        Thread.sleep(1000 * 100)
        calculator.close()
    }
}