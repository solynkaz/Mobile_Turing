package com.example.mobile_turing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_turing.ui.theme.Mobile_TuringTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Turing_IF()
        }
    }
}

@Composable
fun alphabetCell(modifier: Modifier, value: String) {

}

@Composable
fun Turing_IF() {
    val firstLoad = remember { mutableStateOf(true) }
    val buttonTextColor = Color.White
    val buttonColor = Color(0xFF005BFF)
    val buttonModifier = Modifier
        .fillMaxWidth()
        .height(58.dp)
        .padding(start = 2.dp, top = 15.dp)
    val turingAlphabet = remember { ArrayList<MutableState<String>>() }
    val countOfAlphabetCells = remember { mutableStateOf(0) }
    val turingStates = remember { mutableMapOf<String, ArrayList<MutableState<String>>>() }

    if (firstLoad.value) {
        for (i in 0 until 4) {
            turingAlphabet.add(remember { mutableStateOf("") })
            countOfAlphabetCells.value++
        }
    }
    turingStates["q1"] = (arrayListOf(remember { mutableStateOf("") },
        remember { mutableStateOf("") },
        remember { mutableStateOf("") },
        remember { mutableStateOf("") }))
    firstLoad.value = false
    Column(
        Modifier
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        //Ряд алфавита, расширяется
        Row(Modifier.padding(horizontal = 10.dp)) {
            Text(
                "Alphabet\n\nStates",
                Modifier
                    .size(60.dp)
                    .weight(1f)
            )
            for (i in 0 until countOfAlphabetCells.value) {
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 15.sp),
                    isError = false,
                    enabled = true,
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 3.dp, end = 3.dp),
                    value = turingAlphabet[i].value,
                    onValueChange = { letter ->
                        turingAlphabet[i].value = letter
                    },
                )
            }
        }
        for (key in turingStates.keys) {
            Row(Modifier.padding(horizontal = 10.dp)) {
                Text(
                    "\n$key",
                    Modifier
                        .size(60.dp)
                        .weight(1f)
                )
                for (i in 0 until turingStates[key]!!.count()) {
                    OutlinedTextField(
                        textStyle = TextStyle(fontSize = 15.sp),
                        isError = false,
                        enabled = true,
                        singleLine = true,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 3.dp, end = 3.dp),
                        value = turingStates[key]!![i].value,
                        onValueChange = { letter ->
                            turingStates[key]!![i].value = letter
                        },
                    )
                }
            }
        }
        Row() {
            Button(
                enabled = true,
                modifier = buttonModifier
                    .weight(1f)
                    .padding(start = 4.dp, end = 3.dp)
                    .fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor,
                    contentColor = Color.LightGray,
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = {

                }
            ) {
                Text(
                    text = "+", fontSize = 25.sp, fontWeight = FontWeight(1000),
                    color = buttonTextColor,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                enabled = true,
                modifier = buttonModifier
                    .weight(1f)
                    .padding(start = 4.dp, end = 3.dp)
                    .fillMaxHeight(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor,
                    contentColor = Color.LightGray,
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = {

                }
            ) {
                Text(
                    text = "-", fontSize = 25.sp, fontWeight = FontWeight(1000),
                    color = buttonTextColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
