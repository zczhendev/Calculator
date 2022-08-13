package io.zczhendev.calculator.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.zczhendev.calculator.ui.layout.CalculatorKeyboard
import io.zczhendev.calculator.ui.theme.CalculatorTheme
import io.zczhendev.calculator.utils.calculate.Calculator

class MainActivity : ComponentActivity() {

    private val calculator = Calculator { queue, result ->
        val formula = StringBuilder()
        queue.forEach {
            formula.append(it)
        }
        formula.append("=$result")
        Log.i(TAG, "on calculation: $formula")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                Surface(modifier = Modifier.background(Color(0xFFF6F7F9))) {
                    Column {
                        Surface(modifier = Modifier.weight(1f)) {

                        }
                        CalculatorKeyboard { key ->
                            calculator.keyDown(key)
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}
