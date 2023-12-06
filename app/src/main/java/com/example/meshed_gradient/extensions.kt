package com.example.meshed_gradient

import android.content.Context
import android.graphics.Rect
import androidx.annotation.DrawableRes
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.core.content.ContextCompat

fun Modifier.draw9Patch(
    context: Context,
    @DrawableRes ninePatchRes: Int,
) = this.drawBehind {
    drawIntoCanvas {
        ContextCompat.getDrawable(context, ninePatchRes)?.let { ninePatch ->
            ninePatch.run {
                bounds = Rect(0, 0, size.width.toInt(), size.height.toInt())
                draw(it.nativeCanvas)
            }
        }
    }
}

fun String.toHashCode(): Int {
    var hash = 5381
    for (char in this) {
        hash = (hash * 33 + char.code) % Int.MAX_VALUE
    }
    return hash
}

fun String.toColor() = Color(android.graphics.Color.parseColor("#$this"))

fun String.toGraphicColor() = android.graphics.Color.parseColor("#$this")