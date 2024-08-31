package st.masoom.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import st.masoom.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator() {
    var displayText by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf("") }
    var operand2 by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }
    // if invalid value the null
    var result by remember { mutableStateOf<Double?>(null) }


    // typing tasks
    // send to equals function for calculation
    fun onDigitClick(digit: String) {
        // if no operation is selected
        if (operation.isEmpty()) {
            operand1 += digit           // till only first element typed
            displayText = operand1      // show first
        }
        // if operation is selected
        else {
            operand2 += digit           // when second element typed
            displayText = operand2      // show second
        }
    }

    fun onOperationClick(op: String) {
        if (operand1.isNotEmpty()) {
            operation = op
        }
    }

    // for clearing all
    fun onClearClick() {
        operand1 = ""
        operand2 = ""
        operation = ""
        result = null
        displayText = "0"
    }


    //operation
    // it takes operand 1 and 2 and operated and store in result
    fun onEqualsClick() {
        if (operand1.isNotEmpty() && operand2.isNotEmpty() && operation.isNotEmpty()) {
            result = when (operation) {
                "+" -> operand1.toDouble() + operand2.toDouble()
                "-" -> operand1.toDouble() - operand2.toDouble()
                "x" -> operand1.toDouble() * operand2.toDouble()
                "/" -> operand1.toDouble() / operand2.toDouble()
                "%" -> operand1.toDouble() * operand2.toDouble() / 100
                else -> null
            }
            displayText = result.toString()
            operand1 = displayText
            operand2 = ""
            operation = ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Result Text
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = displayText,
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = FontFamily.Serif
        )
        // extra spacer
        Spacer(modifier = Modifier.height(32.dp))


        // using a simple function to make calc buttons
        // few functions for ac , symbols , digits, equals
        Row {
            CalculatorButton(text = "AC", onClick = { onClearClick() })
            CalculatorButton(text = "%", onClick = { onOperationClick("%") })
            CalculatorButton(text = "/", onClick = { onOperationClick("/") })
            CalculatorButton(text = "x", onClick = { onOperationClick("x") })
        }
        Row {
            CalculatorButton(text = "7", onClick = { onDigitClick("7") })
            CalculatorButton(text = "8", onClick = { onDigitClick("8") })
            CalculatorButton(text = "9", onClick = { onDigitClick("9") })
            CalculatorButton(text = "-", onClick = { onOperationClick("-") })
        }
        Row {
            CalculatorButton(text = "4", onClick = { onDigitClick("4") })
            CalculatorButton(text = "5", onClick = { onDigitClick("5") })
            CalculatorButton(text = "6", onClick = { onDigitClick("6") })
            CalculatorButton(text = "+", onClick = { onOperationClick("+") })
        }
        Row {
            CalculatorButton(text = "1", onClick = { onDigitClick("1") })
            CalculatorButton(text = "2", onClick = { onDigitClick("2") })
            CalculatorButton(text = "3", onClick = { onDigitClick("3") })
            CalculatorButton(text = "=", onClick = { onEqualsClick() })
        }
        Row {
            CalculatorButton(text = "0", onClick = { onDigitClick("0") }, modifier = Modifier.weight(2f))
            CalculatorButton(text = ".", onClick = { onDigitClick(".") })
        }
    }
}
//  my code in the function format
@Composable
fun CalculatorButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(96.dp, 64.dp)
            .padding(4.dp)
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorTheme {
        Calculator()
    }
}
