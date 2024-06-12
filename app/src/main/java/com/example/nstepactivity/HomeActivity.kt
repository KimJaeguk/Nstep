package com.example.nstepactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.nstepactivity.ui.theme.NstepActivityTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NstepActivityTheme {
                HomeScreen {
                    // 화면을 클릭했을 때 MainActivity로 전환
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onScreenClick: () -> Unit) {
    // 화면 전체를 클릭 가능하도록 Box로 감싸기
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onScreenClick() },
        contentAlignment = Alignment.Center // 텍스트를 중앙에 배치
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // logo 이미지를 배경으로 설정
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // 이미지가 전체 화면을 덮도록 설정
        )
        Text(
            text = "화면을 클릭하세요",
            fontSize = 24.sp,
            color = androidx.compose.ui.graphics.Color.White // 흰색 글씨로 설정
        )
    }
}
