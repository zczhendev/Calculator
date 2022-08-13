package io.zczhendev.calculator.ui.layout

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import io.zczhendev.calculator.ui.theme.CalculatorTheme
import io.zczhendev.calculator.utils.calculate.key.FunctionKey
import io.zczhendev.calculator.utils.calculate.key.Key
import io.zczhendev.calculator.utils.calculate.key.NumberKey
import io.zczhendev.calculator.utils.calculate.key.OperatorKey

@Composable
fun CalculatorKeyboard(onKeyDown: (key: Key) -> Unit) {
    Surface(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        color = Color.Transparent
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .wrapContentHeight()
            ) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "C",
                        onClick = { onKeyDown(FunctionKey.KEY_CLEAR) },
                        color = Color(0xFFF6ECEB),
                        textColor = Color(0xFFDA3D52)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "(",
                        onClick = { onKeyDown(OperatorKey.KEY_PARENTHESES_LEFT) },
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = ")",
                        onClick = { onKeyDown(OperatorKey.KEY_PARENTHESES_RIGHT) },
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "÷",
                        onClick = { onKeyDown(OperatorKey.KEY_DIV) },
                        color = Color(0xFFF39621),
                        textColor = Color(0xFFF9FFF3)
                    )
                }

            }
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .wrapContentHeight()
            ) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "7",
                        onClick = { onKeyDown(NumberKey.KEY_7) },
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "8",
                        onClick = { onKeyDown(NumberKey.KEY_8) },
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "9",
                        onClick = { onKeyDown(NumberKey.KEY_9) },
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "×",
                        onClick = { onKeyDown(OperatorKey.KEY_MULT) },
                        color = Color(0xFFF39621),
                        textColor = Color(0xFFF9FFF3)
                    )
                }

            }
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .wrapContentHeight()
            ) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        onClick = { onKeyDown(NumberKey.KEY_4) },
                        text = "4",
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "5",
                        onClick = { onKeyDown(NumberKey.KEY_5) },
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "6",
                        onClick = { onKeyDown(NumberKey.KEY_6) },
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "-",
                        onClick = { onKeyDown(OperatorKey.KEY_SUB) },
                        color = Color(0xFFF39621),
                        textColor = Color(0xFFF9FFF3)
                    )
                }

            }
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .wrapContentHeight()
            ) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "1",
                        onClick = { onKeyDown(NumberKey.KEY_1) },
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "2",
                        onClick = { onKeyDown(NumberKey.KEY_2) },
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "3",
                        onClick = { onKeyDown(NumberKey.KEY_3) },
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "+",
                        onClick = { onKeyDown(OperatorKey.KEY_ADD) },
                        color = Color(0xFFF39621),
                        textColor = Color(0xFFF9FFF3)
                    )
                }

            }
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .wrapContentHeight()
            ) {
                Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "0",
                        onClick = { onKeyDown(NumberKey.KEY_0) },
                        width = 160.dp,
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = ".",
                        onClick = { onKeyDown(OperatorKey.KEY_DOT) },
                        color = Color(0xFFE8EFF2),
                        textColor = Color(0xFF343A3C)
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    CalculatorButton(
                        text = "=",
                        onClick = { onKeyDown(OperatorKey.KEY_CALCULATE) },
                        color = Color(0xFFF39621),
                        textColor = Color(0xFFF9FFF3)
                    )
                }

            }

        }
    }
}

@Preview
@Composable
fun PreviewCalculatorKeyboard() {
    CalculatorTheme {
        CalculatorKeyboard {}
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit = {},
    width: Dp = 70.dp,
    height: Dp = 70.dp,
    color: Color,
    textColor: Color
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(height)
            .width(width),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color
        ),
        elevation = null
    ) {
        Text(text = text, color = textColor, fontSize = 20.sp)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewMainLayout() {
    CalculatorTheme {
        Surface(
            Modifier
                .size(90.dp)
                .background(Color(0xFFF6F7F9))
                .padding(10.dp),
            color = Color.Transparent
        ) {
            CalculatorButton(text = "1", color = Color(0xFFEAEEF5), textColor = Color(0xFF1D2126))
        }
    }
}