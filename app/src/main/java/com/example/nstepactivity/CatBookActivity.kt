package com.example.nstepactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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

class CatBook : ComponentActivity() {

    private val cats = listOf(
        Cat("루나", R.drawable.runa, "루나는 터키쉬 앙고라입니다.\n성미가 급하고 재롱을 많이 부립니다."),
        Cat("마일로", R.drawable.mailo, "마일로는 귀여운 갈색고양이입니다."),
        Cat("올리버", R.drawable.oliver, "올리버는 귀여운 러시안블루입니다.\n 장난끼가 많고 개구장이입니다."),
        Cat("레오", R.drawable.reo, "레오는 아메리칸 숏헤어 입니다.\n 줄무늬가 귀엽습니다."),
        Cat("네로", R.drawable.nero, "네로는 검은 고양이입니다.\n 불행을 가져다 준다고 하지만 귀여운걸요."),
        Cat("치즈", R.drawable.cheeze, "치즈는 코리안 숏헤어입니다.\n 말이 많고 활발한 성격을 가지고 있습니다."),
        Cat("섀도우", R.drawable.shadow, "섀도우는 어두운 색의 고양이로,\n 밤에 몰래 돌아다니는 것을 좋아합니다.\n 눈은 밝은 녹색으로 어둠 속에서도 빛납니다."),
        Cat("위스커스", R.drawable.wiskers, "위스커스는 길고 굵은 수염을 가진 고양이입니다.\n 매우 호기심이 많아 새로운 것들을 탐험하는 것을 좋아합니다."),
        Cat("에코", R.drawable.eco, "에코는 소리없이 움직이는 고양이로,\n 발소리 없이 조용히 돌아다니는 것을 좋아합니다."),
        Cat("제트", R.drawable.zet, "제트는 매우 빠르게 달리는 고양이입니다.\n 날렵한 몸매와 빠른 움직임으로 유명합니다."),
        Cat("스타라이트", R.drawable.star, "스타라이트는 별처럼 반짝이는 매력을 가진 고양이입니다.\n 밤에 산책하는 것을 좋아합니다.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val catCollection = intent.getIntegerArrayListExtra("catCollection")?.toList() ?: emptyList()
            CatBookScreen(catCollection)
        }
    }

    @Composable
    fun CatBookScreen(catCollection: List<Int>) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 배경 이미지 설정
            Image(
                painter = painterResource(id = R.drawable.open_book_background), // 종이 질감의 배경 이미지 사용
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // 전체적으로 반투명 흰색 배경 추가
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.5f))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 안내 텍스트
                Text(
                    text = "고양이를 클릭하면 설명을 볼 수 있습니다!!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                LazyColumn(modifier = Modifier.weight(1f)) {
                    itemsIndexed(catCollection.chunked(3)) { _, chunk ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            chunk.forEach { index ->
                                val cat = cats[index]
                                CatListItem(cat)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CatListItem(cat: Cat) {
        var showDialog by remember { mutableStateOf(false) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .clickable { showDialog = true }
        ) {
            // 고양이 이미지
            Image(
                painter = painterResource(id = cat.imageResId),
                contentDescription = cat.name,
                modifier = Modifier.size(100.dp)
            )
            // 고양이 이름 텍스트
            Text(
                text = cat.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold, // 굵은 글씨
                color = Color.Black, // 검은색 글씨
                modifier = Modifier.padding(top = 8.dp)
            )

            // 고양이 설명 다이얼로그
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = cat.name) },
                    text = { Text(text = cat.description) },
                    confirmButton = {
                        Button(onClick = { showDialog = false }) {
                            Text(text = "확인")
                        }
                    }
                )
            }
        }
    }

    data class Cat(val name: String, val imageResId: Int, val description: String)
}
