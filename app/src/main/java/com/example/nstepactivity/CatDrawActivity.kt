package com.example.nstepactivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.nstepactivity.ui.theme.NstepActivityTheme
import kotlin.random.Random

class CatDraw : ComponentActivity() {

    private val cats = listOf(
        Cat("루나", R.drawable.runa, "루나는 터키쉬 앙고라입니다.\n성미가 급하고 재롱을 많이 부립니다."),
        Cat("마일로", R.drawable.mailo, "마일로는 귀여운 갈색고양이입니다."),
        Cat("올리버", R.drawable.oliver, "올리버는 귀여운 러시안블루입니다.\n 장난끼가 많고 개구장이입니다."),
        Cat("레오", R.drawable.reo, "레오는 아메리칸 숏헤어 입니다.\n 줄무늬가 귀엽습니다."),
        Cat("네로", R.drawable.nero, "네로는 검은 고양이입니다.\n 불행을 가져다 준다고 하지만 귀여운걸요."),
        Cat("치즈", R.drawable.cheeze, "치즈는 코리안 숏헤어입니다.\n 말이 많고 활발한 성격을 가지고 있습니다."),
        Cat("섀도우", R.drawable.shadow, "섀도우는 어두운 색의 고양이로, 밤에 몰래 돌아다니는 것을 좋아합니다.\n 눈은 밝은 녹색으로 어둠 속에서도 빛납니다."),
        Cat("위스커스", R.drawable.wiskers, "위스커스는 길고 굵은 수염을 가진 고양이입니다.\n 매우 호기심이 많아 새로운 것들을 탐험하는 것을 좋아합니다."),
        Cat("에코", R.drawable.eco, "에코는 소리없이 움직이는 고양이로, 발소리 없이 조용히 돌아다니는 것을 좋아합니다."),
        Cat("제트", R.drawable.zet, "제트는 매우 빠르게 달리는 고양이입니다.\n 날렵한 몸매와 빠른 움직임으로 유명합니다."),
        Cat("스타라이트", R.drawable.star, "스타라이트는 별처럼 반짝이는 매력을 가진 고양이입니다.\n 밤에 산책하는 것을 좋아합니다.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NstepActivityTheme {
                CatDrawScreen()
            }
        }
    }

    @Composable
    fun CatDrawScreen() {
        var nscoinCount by remember { mutableStateOf(intent.getIntExtra("nscoinCount", 0)) }
        var selectedCats by remember { mutableStateOf(intent.getIntegerArrayListExtra("catCollection")?.toList() ?: emptyList()) }
        var drawnCatIndex by remember { mutableStateOf<Int?>(null) }
        val context = LocalContext.current

        Box(modifier = Modifier.fillMaxSize()) {
            // 배경 이미지 설정
            Image(
                painter = painterResource(id = R.drawable.grass_background),
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                // 보유 코인 텍스트와 이미지
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "보유 코인: $nscoinCount",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 4.dp) // 거리를 좁히기 위해 end padding을 사용
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ns_coin),
                        contentDescription = "NS Coin",
                        modifier = Modifier.size(24.dp)
                    )
                }

                // 뽑은 고양이 이미지와 정보
                drawnCatIndex?.let {
                    val cat = cats[it]
                    Image(
                        painter = painterResource(id = cat.imageResId),
                        contentDescription = cat.name,
                        modifier = Modifier.size(100.dp)
                    )
                    Text(text = cat.name, color = Color.Black, fontWeight = FontWeight.Bold)
                    Text(text = cat.description, color = Color.Black, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 랜덤 고양이 뽑기 버튼
                Button(
                    onClick = {
                        if (nscoinCount >= 100) {
                            val randomCatIndex = Random.nextInt(cats.size)
                            drawnCatIndex = randomCatIndex
                            nscoinCount -= 100
                            if (selectedCats.contains(randomCatIndex)) {
                                nscoinCount += 10
                            } else {
                                selectedCats = selectedCats + randomCatIndex
                            }
                        } else {
                            Toast.makeText(context, "코인이 부족합니다", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier.width(200.dp).padding(vertical = 8.dp)
                ) {
                    Text(text = "랜덤 고양이 뽑기", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 선택 완료 버튼
                Button(
                    onClick = {
                        val resultIntent = Intent().apply {
                            putIntegerArrayListExtra("selectedCats", ArrayList(selectedCats))
                            putExtra("nscoinCount", nscoinCount)
                        }
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier.width(200.dp).padding(vertical = 8.dp)
                ) {
                    Text(text = "선택 완료", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    data class Cat(val name: String, val imageResId: Int, val description: String)
}
