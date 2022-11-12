package com.example.mobile_turing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    val toAddCell = remember { mutableStateOf(false) }
    val toDeleteCell = remember { mutableStateOf(false) }
    val toAddState = remember { mutableStateOf(false) }
    val toDeleteState = remember { mutableStateOf(false) }
    val toProcess = remember { mutableStateOf(false) }

    val firstLoad = remember { mutableStateOf(true) }

    val buttonTextColor = Color.White
    val buttonColor = Color(0xFF005BFF)
    val buttonModifier = Modifier
        .fillMaxWidth()
        .height(58.dp)

    val countOfAlphabetCells = remember { mutableStateOf(0) }
    val turingAlphabet = remember { ArrayList<MutableState<String>>() }
    val turingStates = remember { mutableMapOf<String, ArrayList<MutableState<String>>>() }
    val turingLine = remember { mutableStateOf("") }

    if (firstLoad.value) {
        for (i in 0 until 4) {
            turingAlphabet.add(remember { mutableStateOf("") })
            countOfAlphabetCells.value++
        }
        turingStates["q1"] = (arrayListOf(remember { mutableStateOf("") },
            remember { mutableStateOf("") },
            remember { mutableStateOf("") },
            remember { mutableStateOf("") }))
        firstLoad.value = false

    }
    if (toAddCell.value) {
        turingAlphabet.add(remember { mutableStateOf("") })
        countOfAlphabetCells.value++
        for (key in turingStates.keys) {
            turingStates[key]!!.add(remember { mutableStateOf("") })
        }
        toAddCell.value = false
    }
    if (toDeleteCell.value) {
        turingAlphabet.removeLast()
        countOfAlphabetCells.value--
        for (key in turingStates.keys) {
            turingStates[key]!!.removeLast()
        }
        toDeleteCell.value = false
    }
    if (toAddState.value) {
        val key = "q${turingStates.keys.size + 1}"
        turingStates[key] = arrayListOf()
        for (i in 0 until countOfAlphabetCells.value) {
            turingStates[key]!!.add(remember { mutableStateOf("") })
        }
        toAddState.value = false
    }
    if (toDeleteState.value) {
        turingStates.remove("q${turingStates.keys.size}")
        toDeleteState.value = false
    }

    if (toProcess.value) {
        var temp = turingLine.value.toString()
        val alphabet = mutableListOf<String>()
        for (word in turingAlphabet) {
            alphabet.add(word.value)
        }
        var index = 0
        var keyState = "q1"
        var yacheyka = ""
        var operation = ' '
        var move = ' '
        var i = 0
        var head = 0
        while (true) {
            index = alphabet.indexOf(temp[head].toString())
            yacheyka = if (index == -1) {
                turingStates[keyState]!!.last().value.trim()
            } else {
                turingStates[keyState]!![index].value.trim()
            }
            //Проверка на длину ячейки состояния
            if (yacheyka.length == 1) {
                //Либо R либо L
                when (yacheyka[0]) {
                    'L' -> {
                        if (head > 0) head--
                        else {
                            temp = " $temp"
                        }
                    }
                    'R' -> {
                        if (head < temp.length-1) head++
                        else {
                            temp = "$temp "
                            head++
                        }
                    }
                    '!' -> {
                        break
                    }
                }
            } else {
                val tempArray = temp.toCharArray()
                if (yacheyka[0] == '-') {
                    tempArray[head] = ' '
                } else tempArray[head] = yacheyka[0]
                temp = String(tempArray)
                if (yacheyka[1] == 'L') {
                    if (head > 0) head--
                    else {
                        temp = " $temp"
                    }
                } else if (yacheyka[1] == 'R') {
                    if (head < temp.length-1) head++
                    else {
                        temp = "$temp "
                        head++
                    }
                }
                keyState = ("${yacheyka[2]}${yacheyka[3]}")
            }
        }
        turingLine.value = temp.trim()
        toProcess.value = false
    }
    Column(
        Modifier
            .padding(top = 10.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                textStyle = TextStyle(fontSize = 15.sp),
                isError = false,
                enabled = true,
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
                value = turingLine.value,
                onValueChange = { letter ->
                    turingLine.value = letter
                },
            )
        }
        //Ряд алфавита, расширяется
        Row(Modifier.padding(horizontal = 10.dp)) {
            Text(
                text = "\nAБВ",
                Modifier
                    .size(60.dp)
                    .weight(0.7f),
                fontSize = 14.sp,
                softWrap = false,
                overflow = TextOverflow.Visible
            )
            for (i in 0 until countOfAlphabetCells.value) {
                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 17.sp),
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
        //Ряд состояний, расширяется в зависимости от кол-ва алфавитных ячеек
        for (key in turingStates.keys) {
            Row(Modifier.padding(horizontal = 10.dp)) {
                Text(
                    "\n$key",
                    Modifier
                        .size(60.dp)
                        .weight(0.7f)
                )
                for (i in 0 until turingStates[key]!!.count()) {
                    OutlinedTextField(
                        textStyle = TextStyle(fontSize = 13.sp),
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
        //Кнопки плюс и минус ячейки
        Row(Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            Button(
                enabled = true,
                modifier = buttonModifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(end = 3.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor,
                    contentColor = Color.LightGray,
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    if (countOfAlphabetCells.value < 6) toAddCell.value = true
                }
            ) {
                Text(
                    text = "Добавить ячейку в алфавит",
                    fontSize = 19.sp,
                    fontWeight = FontWeight(600),
                    color = buttonTextColor,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                enabled = true,
                modifier = buttonModifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 3.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor,
                    contentColor = Color.LightGray,
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    if (countOfAlphabetCells.value > 2) toDeleteCell.value = true
                }
            ) {
                Text(
                    text = "Удалить ячейку из афлавита",
                    fontSize = 19.sp,
                    fontWeight = FontWeight(600),
                    color = buttonTextColor,
                    textAlign = TextAlign.Center
                )
            }
        }
        //Кнопки плюс и минус состояние
        Row(Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
            Button(
                enabled = true,
                modifier = buttonModifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(end = 3.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor,
                    contentColor = Color.LightGray,
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    if (turingStates.keys.size < 4) toAddState.value = true
                }
            ) {
                Text(
                    text = "Добавить состояние",
                    fontSize = 19.sp,
                    fontWeight = FontWeight(600),
                    color = buttonTextColor,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                enabled = true,
                modifier = buttonModifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 3.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor,
                    contentColor = Color.LightGray,
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    if (turingStates.keys.size > 1) {
                        toDeleteState.value = true
                    }
                }
            ) {
                Text(
                    text = "Удалить состояние",
                    fontSize = 19.sp,
                    fontWeight = FontWeight(600),
                    color = buttonTextColor,
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(Modifier.fillMaxWidth().padding(top = 5.dp)) {
            Button(
                enabled = true,
                modifier = buttonModifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor,
                    contentColor = Color.LightGray,
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    if (turingLine.value != "") toProcess.value = true
                }
            ) {
                Text(
                    text = "Запустить",
                    fontSize = 19.sp,
                    fontWeight = FontWeight(600),
                    color = buttonTextColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
