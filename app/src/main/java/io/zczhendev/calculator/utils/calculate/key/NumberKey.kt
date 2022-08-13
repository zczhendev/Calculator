package io.zczhendev.calculator.utils.calculate.key

import io.zczhendev.calculator.utils.calculate.exception.KeyNumberOverflowException

class NumberKey private constructor(val number: Byte) : Key {
    init {
        if (number < 0 || number > 9) {
            throw KeyNumberOverflowException()
        }
    }

    override fun toString(): String {
        return number.toString()
    }

    companion object {
        val KEY_0 = NumberKey(0)
        val KEY_1 = NumberKey(1)
        val KEY_2 = NumberKey(2)
        val KEY_3 = NumberKey(3)
        val KEY_4 = NumberKey(4)
        val KEY_5 = NumberKey(5)
        val KEY_6 = NumberKey(6)
        val KEY_7 = NumberKey(7)
        val KEY_8 = NumberKey(8)
        val KEY_9 = NumberKey(9)
    }

}