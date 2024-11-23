package com.example.projet1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projet1.data.DB
import com.example.projet1.ui.theme.Projet1Theme

class Signup : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projet1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->
                    SignupUI(this,modifier = Modifier.padding(innerPadding))
            }  }
        }
    }

    @Composable
    private fun SignupUI(context: Context, modifier: Modifier = Modifier) {
        var name by remember { mutableStateOf("") }
        var mdp by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var confmdp by remember { mutableStateOf("") }
        var userList by remember { mutableStateOf(emptyList<Map<String, String>>()) }
        var selectedUser by remember { mutableStateOf<String?>(null) }
        val dbase = DB(context)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Navigation Row
            Row(
                modifier = Modifier
                    .clickable {
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFFF57BB3),
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Sign Up",
                color = Color(0xFFF57BB3),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(R.drawable.signup),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(20.dp))
            // Input Fields
            SignupTextField(value = name, label = "Name") { name = it }
            Spacer(modifier = Modifier.height(20.dp))
            SignupTextField(value = email, label = "Email") { email = it }
            Spacer(modifier = Modifier.height(20.dp))
            SignupTextField(value = mdp, label = "Password") { mdp = it }
            Spacer(modifier = Modifier.height(20.dp))
            SignupTextField(value = confmdp, label = "Confirm Password") { confmdp = it }
            Spacer(modifier = Modifier.height(40.dp))

            // Sign-Up Button
            Button(
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank() && mdp == confmdp) {
                        val result = dbase.insertUser(name, mdp, email, confmdp)
                        if (result != -1L) {
                            Toast.makeText(context, "User registered successfully!", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Error registering user!", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(context, "Please fill all fields correctly.", Toast.LENGTH_LONG).show()
                        userList = dbase.getAllUsers()
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
                Text("Sign Up", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                        userList = dbase.getAllUsers()
                    val intent = Intent(context, Compts::class.java)
                    context.startActivity(intent)

                },
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF57BB3),
                    contentColor = Color.White,
                )
            ) {
                Text("Comptes", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

    }

    @Composable
    private fun SignupTextField(value: String, label: String, onValueChange: (String) -> Unit) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}
