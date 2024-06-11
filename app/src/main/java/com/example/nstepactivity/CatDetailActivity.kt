package com.example.nstepactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CatDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val catName = intent.getStringExtra("catName")
        val catImageResId = intent.getIntExtra("catImageResId", 0)
        val catDescription = intent.getStringExtra("catDescription")

        setContent {
            CatDetailScreen(catName ?: "", catImageResId, catDescription ?: "")
        }
    }

    @Composable
    fun CatDetailScreen(name: String, imageResId: Int, description: String) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(bottom = 16.dp)
            )
            Text(text = name, fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))
            Text(text = description, fontSize = 16.sp)
        }
    }
}