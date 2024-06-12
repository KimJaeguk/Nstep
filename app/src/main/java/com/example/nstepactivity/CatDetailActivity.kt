package com.example.nstepactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
        var showDialog by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.5f)) // 전체적인 반투명 흰색 배경 추가
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 고양이 이미지
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(bottom = 16.dp)
            )
            // 고양이 이름 텍스트
            Text(text = name, fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
            // 고양이 설명 텍스트
            Text(text = description, fontSize = 16.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            // 설명 다이얼로그
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier.width(200.dp).padding(vertical = 8.dp)
                        ) {
                            Text(text = "확인", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    },
                    text = {
                        Text(text = description, fontSize = 16.sp, color = Color.Black)
                    },
                    title = {
                        Text(text = name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                )
            }
        }
    }
}
