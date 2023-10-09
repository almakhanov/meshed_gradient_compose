package com.example.meshed_gradient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meshed_gradient.ui.theme.MeshedGradientTheme
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.drawColorIndicator
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlin.math.abs

class MainActivity : ComponentActivity() {


    private val days = listOf<GSDay>(
        GSDay(true, false),
        GSDay(true, false),
        GSDay(true, false),
        GSDay(true, false),
        GSDay(false, false),
        GSDay(true, false),
        GSDay(true, false),

        GSDay(true, false),
        GSDay(true, false),
        GSDay(true, false),
        GSDay(true, false),
        GSDay(true, false),
        GSDay(true, false),
        GSDay(true, false),

        GSDay(true, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(true, false),
        GSDay(true, false),
        GSDay(true, false),

        GSDay(false, false),
        GSDay(false, false),
        GSDay(true, false),
        GSDay(true, false),
        GSDay(true, false),
        GSDay(false, false),
        GSDay(true, false),

        GSDay(true, false),
        GSDay(true, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(true, false),
        GSDay(false, false),
        GSDay(false, false),

        GSDay(false, false),
        GSDay(false, false),
        GSDay(true, false),
        GSDay(true, false),
        GSDay(true, false),
        GSDay(true, true),
        GSDay(false, false),

        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
        GSDay(false, false),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeshedGradientTheme {
                var selectedIndex by remember { mutableStateOf(0) }
                var showCalendar by remember { mutableStateOf(false) }

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

                        Text(
                            text = "CLICK TO SHOW CALENDAR",
                            modifier = Modifier
                                .width(100.dp)
                                .align(Alignment.CenterStart)
                                .background(Color.Green)
                                .clickable {
                                    showCalendar = !showCalendar
                                },
                            color = Color.White
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

                    if (showCalendar) {
                        CalendarBox(textColors, days)
                    } else {
                        FluidBox(
                            Modifier
                                .fillMaxWidth()
                                .noRippleClickable {
                                    userHashCode = (1..9999999).random()
                                }, textColors, userHashCode
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun CalendarBox(colors: List<MutableState<Color>>, days: List<GSDay>) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        val width = 204

        Column(modifier = Modifier
            .padding(20.dp)
            .width(width.dp)
            .align(Alignment.Center)) {
            repeat(days.size / 7) { row ->
                Row {
                    val start = row * 7
                    val end = row * 7 + 7
                    getRowData(days.subList(start, end)).forEach {
                        if (it.size == 1) {
                            val startPadding = if (it.startIndex == 0) {
                                0
                            } else {
                                10
                            }
                            val endPadding = if (it.startIndex == 6) {
                                0
                            } else {
                                10
                            }
                            if (it.current) {
                                CurrentDay(it.index)
                            } else {
                                if (it.done) {
                                    CompleteDay(startPadding, endPadding)
                                } else {
                                    EmptyDay(startPadding, endPadding)
                                }
                            }
                        } else {
                            val startPadding = if (it.startIndex == 0) {
                                0
                            } else {
                                10
                            }
                            val endPadding = if (it.startIndex + it.size == 7) {
                                0
                            } else {
                                10
                            }
                            DaysRowView(it.size, startPadding, endPadding, colors)
                        }
                    }
                }
            }
        }

    }

}


@Composable
fun DaysRowView(size: Int, startPadding: Int, endPadding: Int, colors: List<MutableState<Color>>) {
    val brush = Brush.horizontalGradient(colors.map { it.value })
    Box(
        modifier = Modifier
            .padding(start = startPadding.dp, end = endPadding.dp, top = 13.dp, bottom = 13.dp)
            .height(6.dp)
            .width((12 * size + (size - 1) * 20).dp)
            .background(brush, RoundedCornerShape(3.dp))
    )
}

@Composable
fun CurrentDay(index: Int) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(Color.Blue, CircleShape)
    ) {
        Text(text = index.toString(), color = Color.Black, modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun CompleteDay(startPadding: Int, endPadding: Int) {
    Box(
        modifier = Modifier
            .padding(start = startPadding.dp, end = endPadding.dp, top = 10.dp, bottom = 10.dp)
            .size(12.dp)
            .background(Color.Blue, CircleShape)
    )
}

@Composable
fun EmptyDay(startPadding: Int, endPadding: Int) {
    Box(modifier = Modifier
        .padding(start = startPadding.dp, end = endPadding.dp, top = 10.dp, bottom = 10.dp)
        .size(12.dp)
        .background("FF2C2C2E".toColor(), CircleShape))
}

private fun getRowData(days: List<GSDay>): List<GSCalendarDayRow> {
    val list = mutableListOf<GSCalendarDayRow>()
    var counter = 0
    days.forEachIndexed { index, gsDay ->
        if (!gsDay.done) {
            if (counter != 0) {
                list.add(GSCalendarDayRow(index - counter, counter, true))
                counter = 0
            }
            list.add(GSCalendarDayRow(index, 1, false))
        } else if (gsDay.current) {
            if (counter != 0) {
                list.add(GSCalendarDayRow(index - counter, counter, true))
                counter = 0
            }
            list.add(GSCalendarDayRow(index, 1, true, true, 41))
        } else {
            counter++
        }
        if (index == days.size - 1 && counter != 0) {
            list.add(GSCalendarDayRow(index - counter, counter, true))
        }
    }
    return list
}


@Composable
fun FluidBox(modifier: Modifier, colors: List<MutableState<Color>>, hashCode: Int) {
    val maxSize = 200
    val generator = BlobGenerator(hashCode, maxSize)
    Box(
        modifier = modifier
            .background(Color.Black)
            .padding(30.dp), contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(maxSize.dp)
                .blur(40.dp, edgeTreatment = BlurredEdgeTreatment(CircleShape))
        ) {
            val brush = Brush.verticalGradient(colors.map { it.value })
            drawRect(brush)
            colors.forEachIndexed { ind, colorState ->
                val index = ind + 1
                drawCircle(
                    color = colorState.value,
                    radius = generator.findRadius(
                        index,
                        stringHashCode(colorToHexString(colorState.value))
                    ).dp.toPx(),
                    center = Offset(
                        generator.findX(
                            index,
                            stringHashCode(colorToHexString(colorState.value))
                        ).dp.toPx(),
                        generator.findY(
                            index,
                            stringHashCode(colorToHexString(colorState.value))
                        ).dp.toPx(),
                    )
                )
            }
        }
    }
}

class BlobGenerator(private val userHashCode: Int, private val max: Int) {
    fun findRadius(index: Int, colorHashCode: Int): Int {
        val rangeStart = max / 4
        val rangeEnd = max / 3
        val seed = userHashCode * index * colorHashCode
        val random = CustomRandom(seed)
        val rad = random.nextInt(rangeStart, rangeEnd)
        return rad
    }

    fun findX(index: Int, colorHashCode: Int): Int {
        val seed = userHashCode + index + colorHashCode
        val random = CustomRandom(seed)
        val x = random.nextInt(0, max)
        return x
    }

    fun findY(index: Int, colorHashCode: Int): Int {
        val seed = userHashCode - index - colorHashCode
        val random = CustomRandom(seed)
        val y = random.nextInt(0, max)
        return y
    }
}

fun colorToHexString(color: Color): String {
    val argb = color.toArgb()
    return String.format("#%08X", argb).removeRange(1, 3)
}

//fun stringHashCode(input: String): Int {
//    var hash = 5381
//    for (char in input) {
//        hash = (33 * hash + char.code)
//    }
//    return hash
//}
fun stringHashCode(input: String): Int {
    var hash = 5381
    for (char in input) {
        hash = (hash * 33 + char.code) % Int.MAX_VALUE
    }
    return hash
}


class CustomRandom(private var seed: Int) {

    private val multiplier = 271
    private val modulus = 483647

    fun nextInt(rangeStart: Int, rangeEnd: Int): Int {
        val randomValue = generateRandomValue()
        return abs(rangeStart + (randomValue % (rangeEnd - rangeStart + 1)))
    }

    private fun generateRandomValue(): Int {
        seed = (seed * multiplier) % modulus
        return seed
    }
}

fun String.toColor() = Color(android.graphics.Color.parseColor("#$this"))


fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}