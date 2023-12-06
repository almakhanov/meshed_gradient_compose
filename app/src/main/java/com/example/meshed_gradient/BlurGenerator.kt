package com.example.meshed_gradient

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.SweepGradient
import kotlin.math.roundToInt


object BlurGenerator {

    /**
     * [dataList] contains list of Pairs where
     * value of first is GoalType object
     * value of second is completed percentage value
     */
    fun generateBlurSweepGradient(
        dataList: List<Pair<GoalType, Int>>,
        userHashCode: Int,
        blurRadius: Int = 30
    ): Bitmap {
        val size = gs66confirmationCircleImageSize.value
        val bitmap = Bitmap.createBitmap(
            size.toInt(),
            size.toInt(),
            Bitmap.Config.ARGB_8888
        )

        val percents = mutableListOf<Int>()
        val colors = mutableListOf<String>()

        dataList.forEach {
            percents.add(it.second)

            colors.add(it.first.startColor)
            colors.add(it.first.endColor)
        }

        var sum = 0f
        val splitPercents = mutableListOf<Float>().apply {
            percents.forEach {
                sum += it.toFloat() / (2 * 100)
                add(sum)
                sum += it.toFloat() / (2 * 100)
                add(sum)
            }
        }
        val pairs = mutableListOf<Pair<Float, String>>().apply {
            shuffleListWithCustomRandom(colors, userHashCode).forEachIndexed { index, color ->
                add(Pair(splitPercents[index], color))
            }
        }

        val canvas = Canvas(bitmap)

        val gradient: Shader = SweepGradient(
            size / 2,
            size / 2,
            pairs.map { it.second }.map { it.toGraphicColor() }.toIntArray(),
            pairs.map { it.first }.toFloatArray()
        )

        val paint = Paint()
        paint.shader = gradient
        paint.alpha = 242
        canvas.drawRect(0f, 0f, size, size, paint)

        return blurBitmap(bmp = bitmap, scale = 1f, radius = blurRadius) ?: bitmap
    }

    fun generateBlurFluidImage(
        colors: List<Int>,
        generator: BlobGenerator,
        blurRadius: Int = 30
    ): Bitmap {
        val size = gs66confirmationCircleImageSize.value
        val bitmap = Bitmap.createBitmap(
            size.toInt(),
            size.toInt(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        val linearGradient = LinearGradient(
            0f, 0f, 0f, size,
            colors.toIntArray(), // Start and end colors of the gradient
            null, // Positions (null for a uniform distribution)
            Shader.TileMode.CLAMP // Tile mode
        )
        var paint = Paint().apply {
            shader = linearGradient
        }
        canvas.drawRect(0f, 0f, size, size, paint)
        colors.forEachIndexed { ind, colorState ->
            val index = ind + 1
            paint = Paint().apply {
                color = colorState
                paint.isAntiAlias = true
                style = Paint.Style.FILL
            }
            val radius = generator.findRadius(index, colorState)
            val centerX = generator.findX(index, colorState)
            val centerY = generator.findY(index, colorState)
            canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)
        }
        return blurBitmap(bmp = bitmap, scale = 1f, radius = blurRadius) ?: bitmap
    }

    private fun blurBitmap(bmp: Bitmap, scale: Float, radius: Int): Bitmap? {
        var sentBitmap = bmp
        sentBitmap = Bitmap.createScaledBitmap(
            sentBitmap,
            (sentBitmap.width * scale).roundToInt(),
            (sentBitmap.height * scale).roundToInt(),
            false
        )
        val bitmap = sentBitmap.copy(sentBitmap.config, true)
        if (radius < 1) return null
        val w = bitmap.width
        val h = bitmap.height
        val pix = IntArray(w * h)
        bitmap.getPixels(pix, 0, w, 0, 0, w, h)
        val wm = w - 1
        val hm = h - 1
        val wh = w * h
        val div = radius + radius + 1
        val r = IntArray(wh)
        val g = IntArray(wh)
        val b = IntArray(wh)
        var rsum: Int
        var gsum: Int
        var bsum: Int
        var x: Int
        var y: Int
        var i: Int
        var p: Int
        var yp: Int
        var yi: Int
        val vmin = IntArray(Math.max(w, h))
        var divsum = div + 1 shr 1
        divsum *= divsum
        val dv = IntArray(256 * divsum)
        i = 0
        while (i < 256 * divsum) {
            dv[i] = i / divsum
            i++
        }
        yi = 0
        var yw: Int = yi
        val stack = Array(div) { IntArray(3) }
        var stackpointer: Int
        var stackstart: Int
        var sir: IntArray
        var rbs: Int
        val r1 = radius + 1
        var routsum: Int
        var goutsum: Int
        var boutsum: Int
        var rinsum: Int
        var ginsum: Int
        var binsum: Int
        y = 0
        while (y < h) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            i = -radius
            while (i <= radius) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))]
                sir = stack[i + radius]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rbs = r1 - Math.abs(i)
                rsum += sir[0] * rbs
                gsum += sir[1] * rbs
                bsum += sir[2] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                i++
            }
            stackpointer = radius
            x = 0
            while (x < w) {
                r[yi] = dv[rsum]
                g[yi] = dv[gsum]
                b[yi] = dv[bsum]
                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum
                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]
                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]
                if (y == 0) vmin[x] = Math.min(x + radius + 1, wm)
                p = pix[yw + vmin[x]]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]
                rsum += rinsum
                gsum += ginsum
                bsum += binsum
                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer % div]
                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]
                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]
                yi++
                x++
            }
            yw += w
            y++
        }
        x = 0
        while (x < w) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            yp = -radius * w
            i = -radius
            while (i <= radius) {
                yi = Math.max(0, yp) + x
                sir = stack[i + radius]
                sir[0] = r[yi]
                sir[1] = g[yi]
                sir[2] = b[yi]
                rbs = r1 - Math.abs(i)
                rsum += r[yi] * rbs
                gsum += g[yi] * rbs
                bsum += b[yi] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                if (i < hm) {
                    yp += w
                }
                i++
            }
            yi = x
            stackpointer = radius
            y = 0
            while (y < h) {
                pix[yi] =
                    -0x1000000 and pix[yi] or (dv[rsum] shl 16) or (dv[gsum] shl 8) or dv[bsum]
                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum
                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]
                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]
                if (x == 0) vmin[y] = Math.min(y + r1, hm) * w
                p = x + vmin[y]
                sir[0] = r[p]
                sir[1] = g[p]
                sir[2] = b[p]
                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]
                rsum += rinsum
                gsum += ginsum
                bsum += binsum
                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer]
                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]
                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]
                yi += w
                y++
            }
            x++
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h)
        return bitmap
    }

}
