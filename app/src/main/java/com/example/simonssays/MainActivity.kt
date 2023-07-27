package com.example.simonssays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simonssays.ui.theme.SimonsSaysTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.util.Stack
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private val simonSequence = mutableListOf<Pair<Int, Int>>()
    private var isPlayerTurn by mutableStateOf(false)
    private var level by mutableStateOf(1)
    private var playerLost by mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimonsSaysTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                ) {
                    val boxColors = remember { mutableStateListOf<Color>() }
                    repeat(9) { index ->
                        boxColors.add(Color.Green)
                    }
                    if (playerLost){
                        losingScreen()
                    } else {
                    Createbox(boxColors) { row, column ->
                        ChangeBoxColor(boxColors, row, column)
                    }
                    LaunchedEffect(true) {
                        SimonsSaysActions(boxColors)
                    }
                    }

            }
        }
    }

}

    @Preview
    @Composable
    fun CreateboxPreview() {
        val boxColors = remember { mutableStateListOf<Color>() }
        repeat(9) { index ->
            boxColors.add(Color.Green)
        }
        var isPlayerTurn by remember { mutableStateOf(true) } // Start with the player's turn
        var level by remember { mutableStateOf(1) }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Createbox(boxColors) { row, column ->
                // This is a mock click handler for preview. You can remove this if needed.
                println("Button at row $row and column $column was clicked in the preview.")
            }
        }
    }



    @Composable
    fun Createbox(boxColors: MutableList<Color>, onButtonClick: (row: Int, column: Int) -> Unit) {
        repeat(9) { index ->
            boxColors.add(Color.Green)
        }
        val playerSequence = remember { mutableStateListOf<Pair<Int, Int>>() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp),
            Arrangement.Center,
            Alignment.CenterHorizontally,
        ) {
            Box(

                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Green, shape = RoundedCornerShape(20.dp))
                    .border(5.dp, Color.Black, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
                ){
                   Text(
                       text = "Level\n$level",
                       textAlign = TextAlign.Center,
                       color = Color.Black,
                       style = TextStyle(
                           fontSize = 30.sp,
                           fontFamily = FontFamily.Monospace)
                   )

            }
            Spacer(modifier = Modifier.size(100.dp))
            var clickCounter by remember { mutableStateOf(0) }
            for (x in 0 until 3) {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (y in 0 until 3) {
                        val index = x * 3 + y
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(5.dp)
                                .border(5.dp, Color.Black, shape = RoundedCornerShape(20.dp))
                                .background(boxColors[index], shape = RoundedCornerShape(20.dp))
                                .clickable {
                                    if (isPlayerTurn) {
                                        clickCounter++
                                        ChangeBoxColor(boxColors, x, y)
                                        playerSequence.add(Pair(x, y))
                                        if (clickCounter == level) {
                                            isPlayerTurn = false
                                            checkForWinner(simonSequence, playerSequence, boxColors)
                                            clickCounter = 0
                                        }
                                    }

                                }
                        )

                    }
                }
            }
        }
    }
    @Preview
    @Composable
    fun losingScreen() {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),

            ) {

        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier
                    .size(width = 300.dp, height = 100.dp)
                    .background(Color.Green, shape = RoundedCornerShape(20.dp))
                    .border(5.dp, Color.Black, shape = RoundedCornerShape(20.dp))

                ){
                Text(text = "Restar")
            }
        }
    }

    fun checkForWinner(
        simonSequence: MutableList<Pair<Int, Int>>,
        playerSequence: SnapshotStateList<Pair<Int, Int>>,
        boxColors: MutableList<Color>
    ) {
        if (simonSequence.equals(playerSequence)){
            simonSequence.clear()
            playerSequence.clear()
            level++
            CoroutineScope(Dispatchers.Main).launch {
                delay(200)
            SimonsSaysActions(boxColors)
            }
        } else {
            playerLost = true

        }
        simonSequence.clear()
        playerSequence.clear()
    }
    fun SimonsSaysActions(boxColors: MutableList<Color>) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            repeat(level) {
                val newRow = Random.nextInt(3)
                val newColumn = Random.nextInt(3)
                ChangeBoxColor(boxColors, newRow, newColumn)
                simonSequence.add(Pair(newRow, newColumn)) // Add the random click to the generated sequence
delay(200)
            }
            isPlayerTurn = true
            //Remove after testing
            printSimonSequence(simonSequence)
        }

    }

    //Remove after Testing
    fun printSimonSequence(sequence: List<Pair<Int, Int>>) {
        println("Simon Sequence:")
        for ((row, column) in sequence) {
            println("Row: $row, Column: $column")
        }
    }

    fun ChangeBoxColor(boxColors: MutableList<Color>, row: Int, column: Int) {
        val index = row * 3 + column
        boxColors[index] = Color.Blue // or any other color you want to set

        // Use a coroutine to delay the color change back to green after 1 second
        CoroutineScope(Dispatchers.Main).launch {
            delay(200)
            boxColors[index] = Color.Green // Change the color back to green after the delay
        }
    }
}
