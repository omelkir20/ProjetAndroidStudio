package com.example.projet1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projet1.data.DB
import com.example.projet1.ui.theme.Projet1Theme

class forgotpass : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projet1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    forgotp(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun forgotp(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Lock Icon",
                tint = Color(0xFFF57BB3),
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            "Forgot Password ?", color = Color(0xFFF57BB3),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(R.drawable.forgotpassword),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(40.dp))
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text("Email Address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                val db = DB(context)
                val password = db.getPasswordByEmail(text)

                if (password != null) {

                    Toast.makeText(context, "Password: $password", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Email not found!", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF57BB3),
                contentColor = Color.White,
            )
        ) {
            Text(
                "Send", fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    Projet1Theme {
        forgotp("Android")
    }
}
