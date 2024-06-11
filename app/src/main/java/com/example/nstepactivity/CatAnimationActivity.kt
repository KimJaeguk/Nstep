package com.example.nstepactivity

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntSize
import coil.compose.rememberImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class CatAnimationActivity {

    @Composable
    fun catanime(selectedCats: List<Int>, screenSize: IntSize) {
        val cats = listOf(
            R.drawable.runa,
            R.drawable.mailo,
            R.drawable.oliver,
            R.drawable.reo,
            R.drawable.nero,
            R.drawable.cheeze
        )

        val coroutineScope = rememberCoroutineScope()

        selectedCats.forEach { catIndex ->
            val xPos = remember { Animatable(0f) }
            val yPos = remember { Animatable(0f) }

            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    while (true) {
                        val newX = Random.nextFloat() * (screenSize.width - 100)
                        val newY = Random.nextFloat() * (screenSize.height - 100)

                        xPos.animateTo(
                            targetValue = newX,
                            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
                        )
                        yPos.animateTo(
                            targetValue = newY,
                            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
                        )
                        delay(500)
                    }
                }
            }

            Image(
                painter = rememberImagePainter(data = cats[catIndex]),
                contentDescription = null,
                modifier = Modifier.height(100.dp).width(100.dp)
                    .offset(xPos.value.dp, yPos.value.dp)
            )
        }
    }
}
