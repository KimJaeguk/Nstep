package com.example.nstepactivity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import com.example.nstepactivity.ui.theme.NstepActivityTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private var nscoinCount by mutableStateOf(0)
    private var selectedCats by mutableStateOf(emptyList<Int>())
    private var catCollection = mutableStateListOf<Int>()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()

        setContent {
            NstepActivityTheme {
                MainScreen()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestPermission() {
        val permission = Manifest.permission.ACTIVITY_RECOGNITION
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(permission), 0)
        }
    }

    @Composable
    fun MainScreen() {
        var stepCount by remember { mutableStateOf(0L) }

        val context = LocalContext.current
        val sensorManager = context.getSystemService(SensorManager::class.java)
        val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        LaunchedEffect(Unit) {
            val listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event == null || event.sensor.type != Sensor.TYPE_STEP_DETECTOR) {
                        return
                    }
                    stepCount++

                    if (stepCount % 10 == 0L) {
                        nscoinCount += 200
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            sensorManager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "발걸음 수: $stepCount")
            Text(text = "NSCoins: $nscoinCount", modifier = Modifier.padding(bottom = 16.dp))
            Button(onClick = {
                val intent = Intent(context, CatDraw::class.java)
                intent.putExtra("nscoinCount", nscoinCount)
                intent.putIntegerArrayListExtra("catCollection", ArrayList(catCollection))
                resultLauncher.launch(intent)
            }) {
                Text(text = "고양이 뽑으러 가기")
            }
            Button(onClick = {
                val intent = Intent(context, CatBook::class.java)
                intent.putIntegerArrayListExtra("catCollection", ArrayList(catCollection))
                context.startActivity(intent)
            }) {
                Text(text = "고양이 도감 보러 가기")
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.grass_background),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                CatAnimation(selectedCats)
            }
        }
    }

    @Composable
    fun CatAnimation(selectedCats: List<Int>) {
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
                        xPos.animateTo(
                            targetValue = Random.nextFloat() * 1000,
                            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
                        )
                        yPos.animateTo(
                            targetValue = Random.nextFloat() * 1000,
                            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
                        )
                        delay(500)
                    }
                }
            }

            Image(
                painter = painterResource(id = cats[catIndex]),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
                    .offset(xPos.value.dp, yPos.value.dp)
            )
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                nscoinCount = data?.getIntExtra("nscoinCount", 0) ?: 0
                val newSelectedCats =
                    data?.getIntegerArrayListExtra("selectedCats")?.toList() ?: emptyList()

                // 기존 선택된 고양이 리스트 업데이트
                selectedCats = newSelectedCats

                // 새로운 고양이를 선택하거나 이미 보유한 고양이를 다시 선택한 경우 NS 코인 추가
                newSelectedCats.forEach { catIndex ->
                    if (!catCollection.contains(catIndex)) {
                        catCollection.add(catIndex)
                    } else {
                        nscoinCount += 10
                    }
                }
            }
        }
}
