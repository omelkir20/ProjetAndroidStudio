package com.example.projet1

import Flower
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projet1.ui.theme.Projet1Theme

class homePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projet1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListeComposable(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ListeComposable(modifier: Modifier = Modifier) {
    val listFlower = remember { mutableStateListOf(
        Flower(titre = "Tulip", imageUrl = R.drawable.tulip, description = ""),
        Flower(titre = "Jasmine", imageUrl = R.drawable.jasmine, description = ""),
        Flower(titre = "Lily", imageUrl = R.drawable.lily, description = ""),
        Flower(titre = "Sunflower", imageUrl = R.drawable.sunflowers, description = "")
    ) }

    Column(modifier = modifier.fillMaxSize()) {
        AddFlowerCard(listFlower = listFlower)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(listFlower) { fl ->
                ItemComposable(flower = fl)
            }
        }
    }
}

@Composable
fun AddFlowerCard(listFlower: MutableList<Flower>, modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Add a New Flower", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Flower Name") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = image,
                onValueChange = { image = it },
                label = { Text("Image Resource ID or URL") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (name.isNotEmpty() && description.isNotEmpty()) {
                        listFlower.add(
                            Flower(
                                titre = name,
                                imageUrl = image.toIntOrNull() ?: R.drawable.placeholder,
                                description = description
                            )
                        )
                        name = ""
                        image = ""
                        description = ""
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add")
            }
        }
    }
}

@Composable
fun ItemComposable(modifier: Modifier = Modifier, flower: Flower) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .padding(16.dp)
            .clickable {
                val intent = Intent(context, Detail::class.java)
                intent.putExtra("titre", flower.titre)
                intent.putExtra("image", flower.imageUrl)
                intent.putExtra("description", flower.description)
                context.startActivity(intent)
            }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = flower.titre,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp),
                fontFamily = FontFamily.Serif
            )
            Image(
                painter = painterResource(id = flower.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .padding(8.dp)
                    .height(250.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = flower.description, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    Projet1Theme {
        ListeComposable()
    }
}
