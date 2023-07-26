package com.example.simonssays

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simonssays.ui.theme.SimonsSaysTheme


class MainMenu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimonsSaysTheme {
          Surface(
              modifier = Modifier.fillMaxSize(),
              color = Color.White

          ) {
                  MainMenuFun(this)
          }
            }
        }
    }
}


@Composable
fun MainMenuFun(activity: Activity){
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .offset(y = 150.dp)



    ) {
        Box(

            modifier = Modifier
                .padding(20.dp)
                .size(width = 300.dp, height = 100.dp)
                .background(Color.Green, shape = RoundedCornerShape(20.dp))
                .clickable {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                },
            contentAlignment = Alignment.Center
        ){
            Alignment.Center
            Text(
                text = "Start Game",
                color = Color.White
            )
        }
        Box(

            modifier = Modifier
                .size(width = 300.dp, height = 100.dp)
                .background(Color.Green, shape = RoundedCornerShape(20.dp))
                .clickable {
                    activity.finish()
                },
            contentAlignment = Alignment.Center
        ){
            Alignment.Center
            Text(
                text = "Quit",
                color = Color.White
            )
        }
    }
}



