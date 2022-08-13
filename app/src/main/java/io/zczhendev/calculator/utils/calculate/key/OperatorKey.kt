package io.zczhendev.calculator.utils.calculate.key

class OperatorKey private constructor(val operator: String) : Key {

    companion object {
        val KEY_DOT = OperatorKey(".")
        val KEY_ADD = OperatorKey("+")
        val KEY_SUB = OperatorKey("-")
        val KEY_MULT = OperatorKey("×")
        val KEY_DIV = OperatorKey("÷")
        val KEY_CALCULATE = OperatorKey("=")

        val KEY_PARENTHESES_LEFT = OperatorKey("(")
        val KEY_PARENTHESES_RIGHT = OperatorKey(")")
    }

    override fun toString(): String {
        return operator
    }

}