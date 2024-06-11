package com.example.nstepactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

class CatBook : ComponentActivity() {

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
            val catCollection = intent.getIntegerArrayListExtra("catCollection")?.toList() ?: emptyList()
            CatBookScreen(catCollection)
        }
    }

    @Composable
    fun CatBookScreen(catCollection: List<Int>) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
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

    @Composable
    fun CatListItem(cat: Cat) {
        var showDialog by remember { mutableStateOf(false) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .clickable { showDialog = true }
        ) {
            val painter = rememberAsyncImagePainter(model = "file:///android_asset/${cat.imageResId}")
            Image(
                painter = painter,
                contentDescription = cat.name,
                modifier = Modifier.size(100.dp)
            )
            Text(text = cat.name, fontSize = 20.sp, modifier = Modifier.padding(top = 8.dp))

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

    data class Cat(val name: String, val imageResId: String, val description: String)
}
