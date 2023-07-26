package com.example.simonssays

import android.os.Bundle
import android.util.Log
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.simonssays.ui.theme.SimonsSaysTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private val generatedSequence = mutableListOf<Pair<Int, Int>>()
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

                    Createbox(boxColors) { row, column ->
                        printButtonPosition(row, column)
                        ChangeBoxColor(boxColors, row, column)

                    }
                    LaunchedEffect(true) {
                        SimonsSaysActions(boxColors)
                    }
            }
        }
    }

}


    //@Preview
    //@Composable
   // fun CreateboxPreview() {
     //   Createbox { row, column, boxColors: MutableList<Color> ->
            // Handle button click logic here in the preview
       //     println("Button at row $row and column $column was clicked in the preview.")
        //}
    //}

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
                                    //Testing Button positions (Remove After testing)
                                    printButtonPosition(x, y)
                                    ChangeBoxColor(boxColors, x, y)
                                    playerSequence.add(Pair(x, y))
                                }
                        )
                        if (playerSequence == generatedSequence) {

                        } else {

                        }

                    }
                }
            }
        }
    }

    //testing buttons position ( Remove after testing)
    private fun printButtonPosition(row: Int, column: Int) {
        // Print the position of the button that was clicked
        println("Button at row $row and column $column was clicked.")
    }

    fun SimonsSaysActions(boxColors: MutableList<Color>) {
        var level: Int
        level = 3
        CoroutineScope(Dispatchers.Main).launch {
            repeat(level) {
                val newRow = Random.nextInt(3)
                val newColumn = Random.nextInt(3)
                ChangeBoxColor(boxColors, newRow, newColumn)
                generatedSequence.add(Pair(newRow, newColumn)) // Add the random click to the generated sequence
                delay(1000)
            }
        }
    }

    fun ChangeBoxColor(boxColors: MutableList<Color>, row: Int, column: Int) {
        val index = row * 3 + column
        boxColors[index] = Color.Blue // or any other color you want to set

        // Use a coroutine to delay the color change back to green after 1 second
        CoroutineScope(Dispatchers.Main).launch {
            delay(300)
            boxColors[index] = Color.Green // Change the color back to green after the delay
        }
    }
}
