package io.zczhendev.calculator.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.zczhendev.calculator.ui.layout.CalculatorKeyboard
import io.zczhendev.calculator.ui.theme.CalculatorTheme
import io.zczhendev.calculator.utils.calculate.Calculator
import io.zczhendev.calculator.utils.calculate.key.FunctionKey
import io.zczhendev.calculator.utils.calculate.key.NumberKey
import io.zczhendev.calculator.utils.calculate.key.OperatorKey
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {

    private val decimalFormat = DecimalFormat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var rememberExpText by remember { mutableStateOf("") }
            var rememberResult by remember {
                mutableStateOf("")
            }

            val calculator = Calculator { queue, result ->
                val formula = StringBuilder()
                queue.forEach {
                    if (it is Calculator.NumberQueueNode) {
                        formula.append(decimalFormat.format(it.number))
                    } else if (it is Calculator.OperatorQueueNode) {
                        formula.append(it)
                    }
                }

                rememberExpText = formula.toString()
                rememberResult = decimalFormat.format(result)
                formula.append("=$result")
                Log.i(TAG, "on calculation: $formula")
            }
            CalculatorTheme {
                Surface(modifier = Modifier.background(Color(0xFFF6F7F9))) {
                    Column {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(40.dp)
                        ) {
                            Spacer(Modifier.weight(1f))
                            Text(
                                text = rememberExpText,
                                textAlign = TextAlign.Right,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .alpha(.4f)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = rememberResult,
                                fontSize = 50.sp,
                                textAlign = TextAlign.Right,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        CalculatorKeyboard(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) { key ->
                            calculator.keyDown(key)
                            if (key is NumberKey || key is OperatorKey) {
                                if (key == OperatorKey.KEY_CALCULATE) {
                                    return@CalculatorKeyboard
                                }
                                rememberResult += key.toString()
                            } else if (key is FunctionKey) {
                                if (key == FunctionKey.KEY_CLEAR) {
                                    rememberResult = ""
                                    rememberExpText = ""
                                }
                            }
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
