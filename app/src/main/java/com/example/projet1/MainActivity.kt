package com.example.projet1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projet1.ui.theme.Projet1Theme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.HistoricalChange
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projet1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Login(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}
@Composable
fun Login(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val email="omelkirrebei22@gmail.com"
    val pass="202003"
    val prefs = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    var isLoggedIn by remember { mutableStateOf(prefs.getBoolean("isLoggedIn", false)) }
    var text by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var showMessage by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.login),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text("User name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        if (showMessage) {
            Text("Please enter the user name !", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = text2,
            onValueChange = { newText -> text2 = newText },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (showMessage) {
            Text("Please enter the password !", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                showMessage = text.isEmpty() && text2.isEmpty()
                if (text == email && text2 == pass) {
                    // Save login state in SharedPreferences
                    val editor = prefs.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()
                }else{
                    val intent = Intent(context,homePage::class.java)
                    context.startActivity(intent)
                }
            },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF57BB3),
                contentColor = Color.White
            )
        ) {
            Text("Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val intent = Intent(context, signup::class.java)
                context.startActivity(intent)

            },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF57BB3),
                contentColor = Color.White
            )
        ) {
            Text("Sign Up", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Forgot Password?",
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable {
                    val intent = Intent(context, forgotpass::class.java)
                    context.startActivity(intent)
                }
                .padding(8.dp)
        )
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Projet1Theme {
        Login("Android")
    }
}