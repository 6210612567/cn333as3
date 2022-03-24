package com.example.biggernumbercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BiggerNumber()
        }
    }
}

fun randomNumber(): Int {
    val random = Random()
    return random.nextInt(1000)
}

@Composable
fun BiggerNumber(){
    var rNum by rememberSaveable { mutableStateOf(randomNumber()) }
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Try to guess the number I'm thinking of from  1 - 1000!",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Inputs(rNum = rNum)
    }
}

@Composable
fun CheckButton(input: String, ranNum: Int) {
    var rNum by rememberSaveable { mutableStateOf(ranNum) }
    var count = remember { mutableStateOf(0)}
    var text by rememberSaveable { mutableStateOf("") }
    var ans by rememberSaveable { mutableStateOf(false) }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .padding(horizontal = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween
            ){
        Button(
            onClick = {
                when {
                    input == "" -> {
                        text = "Please fill your guess number!"
                    }
                    input.toInt() == rNum -> {
                        ans = true
                        count.value++
                    }
                    input.toInt() > rNum -> {
                        text = "Hint: It's to higher!"
                        count.value++
                    }
                    input.toInt() < rNum -> {
                        text = "HINT: It's to lower!"
                        count.value++
                    }
                }
            }
        )

        {
            Text(
                text = "CHECK", fontSize = 16.sp,
            )
        }
        Button(
            onClick = {
                count.value = 0
                ans = false
                rNum = randomNumber()
                text = ""
            }
        ) {
            Text(text = "RESET", fontSize = 16.sp)
        }
    }
    if (ans){
        Text(text = "Congratulations , your number is correct!",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
        )

        Text(
            text = "You have played ${count.value} times",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
        )
    }
    else{
        Text(text = "$text",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun Inputs(rNum: Int) {
    var text by rememberSaveable { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it
        },
        label = { Text(text = "Enter your guess")},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction = ImeAction.Done),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 30.dp)
    )

    CheckButton(input = text, ranNum = rNum)

}
