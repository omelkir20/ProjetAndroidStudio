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
import androidx.compose.ui.graphics.Color
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
        Flower(titre = "Tulip", imageUrl = R.drawable.tulip, description = "The tulip is a colorful spring flower with a cup-shaped bloom and smooth green leaves. It comes in many colors like red, yellow, pink, and purple. Originally from Central Asia, tulips became famous in Europe, especially in the Netherlands. They are popular in gardens and bouquets."),
        Flower(titre = "Jasmine", imageUrl = R.drawable.jasmine, description = "Jasmine is a fragrant flowering plant known for its beautiful, delicate white or yellow flowers. It is often associated with warm climates and is commonly found in gardens and landscapes. The flowers bloom in clusters and release a sweet, heady scent, especially at night, making them popular in perfumes and essential oils."),
        Flower(titre = "Lily", imageUrl = R.drawable.lily, description = "The lily is a striking flowering plant known for its large, showy blooms and elegant shape. Lilies come in various colors, including white, orange, pink, yellow, and purple, and they often have a lovely fragrance. They are commonly found in gardens and are popular in floral arrangements.\n"),
        Flower(titre = "Sunflower", imageUrl = R.drawable.sunflowers, description = "The sunflower is a bright, cheerful flower known for its large, round face that follows the sun across the sky. Characterized by its vibrant yellow petals and a dark center filled with seeds, the sunflower is both beautiful and functional. It grows tall on sturdy stems and can reach heights of over six feet. Sunflowers are not only popular in gardens but are also cultivated for their seeds and oil, which are used in cooking and snacks. Symbolically, sunflowers represent happiness, warmth, and loyalty, making them a favorite choice for bouquets and floral arrangements.")
    ) }

    var flowerToEdit by remember { mutableStateOf<Flower?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        AddFlowerCard(listFlower = listFlower)

        flowerToEdit?.let { flower ->
            EditFlowerDialog(
                flower = flower,
                onDismiss = { flowerToEdit = null },
                onSave = { updatedFlower ->
                    val index = listFlower.indexOf(flower)
                    if (index != -1) {
                        listFlower[index] = updatedFlower
                    }
                    flowerToEdit = null
                }
            )
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(listFlower) { fl ->
                ItemComposable(
                    flower = fl,
                    onEdit = { flowerToEdit = it },
                    onDelete = { listFlower.remove(it) }
                )
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
fun ItemComposable(modifier: Modifier = Modifier, flower: Flower, onEdit: (Flower) -> Unit, onDelete: (Flower) -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .padding(16.dp)
            .clickable {
                val intent = Intent(context, Detail::class.java)
                intent.putExtra("titre", flower.titre)
                intent.putExtra("image", flower.imageUrl)
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
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onEdit(flower) },
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Text("Modifier")
                }
                Button(
                    onClick = { onDelete(flower) },
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Supprimer")
                }
            }
        }
    }
}

@Composable
fun EditFlowerDialog(flower: Flower, onDismiss: () -> Unit, onSave: (Flower) -> Unit) {
    var name by remember { mutableStateOf(flower.titre) }
    var description by remember { mutableStateOf(flower.description) }
    var image by remember { mutableStateOf(flower.imageUrl.toString()) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Modifier la Fleur") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Nom de la fleur") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                TextField(value = image, onValueChange = { image = it }, label = { Text("Image Resource ID") })
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        flower.copy(
                            titre = name,
                            description = description,
                            imageUrl = image.toIntOrNull() ?: R.drawable.placeholder
                        )
                    )
                }
            ) {
                Text("Enregistrer")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    Projet1Theme {
        ListeComposable()
    }
}
