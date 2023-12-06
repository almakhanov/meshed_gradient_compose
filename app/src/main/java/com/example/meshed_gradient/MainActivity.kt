package com.example.meshed_gradient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meshed_gradient.ui.theme.MeshedGradientTheme
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.drawColorIndicator
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

class MainActivity : ComponentActivity() {

    private val red = 26
    private val green = 19
    private val blue = 55

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeshedGradientTheme {
                var selectedIndex by remember { mutableStateOf(0) }
                val controller = rememberColorPickerController()

                val hexCodes = listOf(
                    remember { mutableStateOf("FFA6FF8C") },
                    remember { mutableStateOf("FF0EE2FF") },
                    remember { mutableStateOf("FFEBFFE6") },
                    remember { mutableStateOf("FFFFC5FF") },
                    remember { mutableStateOf("FFD1FAFF") },
                    remember { mutableStateOf("FF6C9CFF") },
                )
                val textColors = listOf(
                    remember { mutableStateOf(hexCodes[0].value.toColor()) },
                    remember { mutableStateOf(hexCodes[1].value.toColor()) },
                    remember { mutableStateOf(hexCodes[2].value.toColor()) },
                    remember { mutableStateOf(hexCodes[3].value.toColor()) },
                    remember { mutableStateOf(hexCodes[4].value.toColor()) },
                    remember { mutableStateOf(hexCodes[5].value.toColor()) },
                )

                Column {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        HsvColorPicker(
                            modifier = Modifier
                                .size(150.dp)
                                .padding(10.dp)
                                .align(Alignment.Center),
                            controller = controller,
                            drawOnPosSelected = {
                                drawColorIndicator(
                                    controller.selectedPoint.value,
                                    controller.selectedColor.value,
                                )
                            },
                            onColorChanged = { colorEnvelope ->
                                hexCodes[selectedIndex].value = colorEnvelope.hexCode
                                textColors[selectedIndex].value = colorEnvelope.color
                            },
                            initialColor = "A6FF8C".toColor(),
                        )
                    }
                    AlphaSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp, 8.dp)
                            .height(16.dp)
                            .align(Alignment.CenterHorizontally),
                        controller = controller,
                    )
                    BrightnessSlider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp, 8.dp)
                            .height(16.dp)
                            .align(Alignment.CenterHorizontally),
                        controller = controller,
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .width(100.dp)
                                .clickable { selectedIndex = 0 }
                                .padding(4.dp)
                                .border(
                                    3.dp,
                                    color = if (selectedIndex == 0) Color.Black else Color.LightGray
                                )
                        ) {
                            Text(
                                text = "#${hexCodes[0].value}",
                                color = textColors[0].value,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                                    .background(textColors[0].value)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .width(100.dp)
                                .clickable { selectedIndex = 1 }
                                .padding(4.dp)
                                .border(
                                    3.dp,
                                    color = if (selectedIndex == 1) Color.Black else Color.LightGray
                                )
                        ) {
                            Text(
                                text = "#${hexCodes[1].value}",
                                color = textColors[1].value,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                                    .background(textColors[1].value)
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .width(100.dp)
                                .clickable { selectedIndex = 2 }
                                .padding(4.dp)
                                .border(
                                    3.dp,
                                    color = if (selectedIndex == 2) Color.Black else Color.LightGray
                                )
                        ) {
                            Text(
                                text = "#${hexCodes[2].value}",
                                color = textColors[2].value,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                                    .background(textColors[2].value)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .width(100.dp)
                                .clickable { selectedIndex = 3 }
                                .padding(4.dp)
                                .border(
                                    3.dp,
                                    color = if (selectedIndex == 3) Color.Black else Color.LightGray
                                )
                        ) {
                            Text(
                                text = "#${hexCodes[3].value}",
                                color = textColors[3].value,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                                    .background(textColors[3].value)
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .width(100.dp)
                                .clickable { selectedIndex = 4 }
                                .padding(4.dp)
                                .border(
                                    3.dp,
                                    color = if (selectedIndex == 4) Color.Black else Color.LightGray
                                )
                        ) {
                            Text(
                                text = "#${hexCodes[4].value}",
                                color = textColors[4].value,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                                    .background(textColors[4].value)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .width(100.dp)
                                .clickable { selectedIndex = 5 }
                                .padding(4.dp)
                                .border(
                                    3.dp,
                                    color = if (selectedIndex == 5) Color.Black else Color.LightGray
                                )
                        ) {
                            Text(
                                text = "#${hexCodes[5].value}",
                                color = textColors[5].value,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                                    .background(textColors[5].value)
                            )
                        }
                    }

                    var userHashCode by remember {
                        mutableStateOf(123456)
                    }
                    Text(
                        text = "UserID: $userHashCode", modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                    Box(modifier = Modifier.fillMaxWidth().height(500.dp).background(Color.Black)) {
                        FluidBox(
                            modifier = Modifier
                                .padding(start = 100.dp)
                                .size(gs66confirmationCircleImageSize)
                                .align(Alignment.CenterStart),
                            hexCodes.map { it.value },
                            userHashCode
                        )

                        SweepCircle(Modifier.padding(start = 170.dp, top = 70.dp)
                            .size(gs66confirmationCircleImageSize)
                            .align(Alignment.CenterStart), hexCodes.map { it.value }, userHashCode)
                    }
                }
            }
        }
    }
}

val gs66confirmationCircleImageSize = 120.dp

@Composable
fun FluidBox(modifier: Modifier, colors: List<String>, hashCode: Int) {
    val maxSize = 120
    val generator = BlobGenerator(hashCode, maxSize)
    Box(modifier.clip(CircleShape)) {
        val graphicColors = colors.map { it.toGraphicColor() }
        val image = BlurGenerator.generateBlurFluidImage(graphicColors, generator)
        Image(bitmap = image.asImageBitmap(), contentDescription = "", modifier = Modifier.fillMaxSize())
    }
}


@Composable
fun SweepCircle(modifier: Modifier, colors: List<String>, userHashCode: Int) {
    Box(modifier.clip(CircleShape)) {
        val dataList = listOf(
            Pair(GoalType(0, "", colors[0], colors[1]), 26),
            Pair(GoalType(0, "", colors[2], colors[3]), 19),
            Pair(GoalType(0, "", colors[4], colors[5]), 55)
        )
        val image = BlurGenerator.generateBlurSweepGradient(dataList, userHashCode)
        Image(bitmap = image.asImageBitmap(), contentDescription = "", modifier = Modifier.fillMaxSize())
    }
}