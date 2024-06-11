package com.example.nstepactivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlin.random.Random

class CatDraw : ComponentActivity() {

    private val cats = listOf(
        Cat("루나", "runa_m.gif", "루나는 터키쉬 앙고라입니다.\n성미가 급하고 재롱을 많이 부립니다."),
        Cat("마일로", "mailo_m.gif", "마일로는 귀여운 갈색고양이입니다."),
        Cat("올리버", "oliver_m.gif", "올리버는 귀여운 러시안블루입니다.\n 장난끼가 많고 개구장이입니다."),
        Cat("레오", "reo_m.gif", "레오는 아메리칸 숏헤어 입니다.\n 줄무늬가 귀엽습니다."),
        Cat("네로", "nero_m.gif", "네로는 검은 고양이입니다.\n 불행을 가져다 준다고 하지만 귀여운걸요."),
        Cat("치즈", "cheeze_m.gif", "치즈는 코리안 숏헤어입니다.\n 말이 많고 활발한 성격을 가지고 있습니다.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatDrawScreen()
        }
    }

    @Composable
    fun CatDrawScreen() {
        var nscoinCount by remember { mutableStateOf(intent.getIntExtra("nscoinCount", 0)) }
        var selectedCats by remember { mutableStateOf(intent.getIntegerArrayListExtra("catCollection")?.toList() ?: emptyList()) }
        var drawnCatIndex by remember { mutableStateOf<Int?>(null) }
        val context = LocalContext.current

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("보유 코인: $nscoinCount", modifier = Modifier.padding(16.dp))

            drawnCatIndex?.let {
                val cat = cats[it]
                AsyncImage(
                    model = "file:///android_asset/${cat.imageResId}",
                    contentDescription = cat.name,
                    modifier = Modifier.size(100.dp)
                )
                Text(text = cat.name)
                Text(text = cat.description)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
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
            }) {
                Text(text = "랜덤 고양이 뽑기")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val resultIntent = Intent().apply {
                        putIntegerArrayListExtra("selectedCats", ArrayList(selectedCats))
                        putExtra("nscoinCount", nscoinCount)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "선택 완료")
            }
        }
    }

    data class Cat(val name: String, val imageResId: String, val description: String)
}
