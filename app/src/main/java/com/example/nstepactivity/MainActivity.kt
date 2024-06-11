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
import android.util.DisplayMetrics
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nstepactivity.ui.theme.NstepActivityTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private var nscoinCount by mutableStateOf(0)
    private var stepCount by mutableStateOf(0L)
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
        val context = LocalContext.current
        val activity = (context as? Activity)
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val screenSize = IntSize(displayMetrics.widthPixels, displayMetrics.heightPixels)

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

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.grass_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Text(
                    text = "발걸음 수: $stepCount",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "NSCoins: $nscoinCount",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

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

                if (catCollection.isNotEmpty()) {
                    CatAnimation(catCollection, screenSize)
                }
            }
        }
    }

    @Composable
    fun CatAnimation(catCollection: List<Int>, screenSize: IntSize) {
        val coroutineScope = rememberCoroutineScope()

        catCollection.forEach { catIndex ->
            val xPos = remember { Animatable(0f) }
            val yPos = remember { Animatable(0f) }

            val density = LocalDensity.current

            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    val catSizePx = with(density) { 100.dp.toPx() }

                    while (true) {
                        val newX = Random.nextFloat() * (screenSize.width - catSizePx)
                        val newY = Random.nextFloat() * (screenSize.height - catSizePx)

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

            AsyncImage(
                model = "file:///android_asset/${getCatImageResource(catIndex)}",
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .offset { IntOffset(xPos.value.toInt(), yPos.value.toInt()) }
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(constraints.maxWidth, constraints.maxHeight) {
                            placeable.placeRelative(
                                x = xPos.value.toInt().coerceIn(0, screenSize.width - placeable.width),
                                y = yPos.value.toInt().coerceIn(0, screenSize.height - placeable.height)
                            )
                        }
                    }
            )
        }
    }

    private fun getCatImageResource(catIndex: Int): String {
        return when (catIndex) {
            0 -> "runa_m.gif"
            1 -> "mailo_m.gif"
            2 -> "oliver_m.gif"
            3 -> "reo_m.gif"
            4 -> "nero_m.gif"
            5 -> "cheeze_m.gif"
            else -> ""
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                nscoinCount = data?.getIntExtra("nscoinCount", 0) ?: 0
                val newSelectedCats =
                    data?.getIntegerArrayListExtra("selectedCats")?.toList() ?: emptyList()

                newSelectedCats.forEach { catIndex ->
                    if (!catCollection.contains(catIndex)) {
                        catCollection.add(catIndex)
                    } else {
                        nscoinCount += 10
                    }
                }

                selectedCats = newSelectedCats
            }
        }
}
