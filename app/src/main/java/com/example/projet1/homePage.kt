package com.example.projet1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Import items here
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.projet1.data.Flower
import com.example.projet1.ui.theme.Detail
import com.example.projet1.ui.theme.Projet1Theme

class homePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projet1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListeComposable(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun ListeComposable(modifier: Modifier = Modifier) {
    val listFlower = remember { mutableListOf(
        Flower(titre = "Tulip", imageUrl = R.drawable.tulip),
        Flower(titre = "Jasmine", imageUrl = R.drawable.jasmine),
        Flower(titre = "Lily", imageUrl = R.drawable.lily),
        Flower(titre = "Sunflower", imageUrl = R.drawable.sunflowers)
    ) }
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(listFlower) { fl ->
            ItemComposable(flower = fl)
        }
    }
}


@Composable
fun ItemComposable(modifier: Modifier = Modifier, flower: Flower) {
    var fl by remember { mutableStateOf("") }
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
                text = flower.titre, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp),fontFamily = FontFamily.Serif,
            )
            Image(
                painter = painterResource(id = flower.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50.dp))
                    .padding(8.dp)
                    .height(250.dp),
                contentScale = ContentScale.Crop,

                )

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
