package com.calyrsoft.budgetapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.calyrsoft.budgetapp.ui.theme.InventoryappTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
//        enableEdgeToEdge()
        setContent {
            InventoryappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginCompose(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LoginCompose(name: String, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    Column() {
        Text( text = "Login")
        TextField(
            value = text,
            onValueChange = {
                newText -> text = newText
            }
        )

    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview2() {
//    InventoryappTheme {
//        Greeting2("Android")
//    }
//}