package io.zczhendev.calculator.utils.calculate.exception

class UnsupportedOperatorException(operator: String) : Exception("不支持的运算符: $operator")
