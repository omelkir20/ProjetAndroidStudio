package com.example.projet1.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projet1.R


class Detail : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Projet1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val data1=intent.getStringExtra("titre")?:"no_data"
                    val data2=intent.getIntExtra("image", R.drawable.forgotpassword)
                    Greeting(
                        modifier = Modifier.padding(innerPadding),
                        titre=data1,
                        image=data2
                    )
                }
            }
        }
    }
}
@Composable
fun Greeting(modifier: Modifier = Modifier,titre:String,image:Int) {

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text =titre,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontFamily = FontFamily.Serif,
            modifier = modifier.padding(bottom = 10.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter =painterResource(id = image),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(50.dp)).height(250.dp),
            contentScale = ContentScale.Crop
        )
        if (titre=="Tulip"){
            Text(text = "The tulip is a colorful spring flower with a cup-shaped bloom and smooth green leaves. It comes in many colors like red, yellow, pink, and purple. Originally from Central Asia, tulips became famous in Europe, especially in the Netherlands. They are popular in gardens and bouquets.",
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                modifier = modifier.padding(top = 10.dp),  )

        }
        else if (titre=="Jasmine"){
            Text(text = "Jasmine is a fragrant flowering plant known for its beautiful, delicate white or yellow flowers. It is often associated with warm climates and is commonly found in gardens and landscapes. The flowers bloom in clusters and release a sweet, heady scent, especially at night, making them popular in perfumes and essential oils.",
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                modifier = modifier.padding(top = 10.dp))


        }
        else if (titre=="Lily"){
            Text(text = "The lily is a striking flowering plant known for its large, showy blooms and elegant shape. Lilies come in various colors, including white, orange, pink, yellow, and purple, and they often have a lovely fragrance. They are commonly found in gardens and are popular in floral arrangements.",
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                modifier = modifier.padding(top = 10.dp))


        }
        else if (titre=="Sunflower"){
            Text(text = "The sunflower is a bright, cheerful flower known for its large, round face that follows the sun across the sky. Characterized by its vibrant yellow petals and a dark center filled with seeds, the sunflower is both beautiful and functional. It grows tall on sturdy stems and can reach heights of over six feet. Sunflowers are not only popular in gardens but are also cultivated for their seeds and oil, which are used in cooking and snacks. Symbolically, sunflowers represent happiness, warmth, and loyalty, making them a favorite choice for bouquets and floral arrangements.",
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                modifier = modifier.padding(top = 10.dp))


        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    Projet1Theme {
        Greeting(titre="", image =33)
    }
}
