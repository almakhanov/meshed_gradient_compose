package com.example.meshed_gradient

import kotlin.math.abs

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

fun <T> shuffleListWithCustomRandom(list: List<T>, userHashCode: Int): List<T> {
    val mutableList = list.toMutableList()
    val customRandom = CustomRandom(userHashCode)

    for (i in mutableList.size - 1 downTo 1) {
        val j = customRandom.nextInt(0, i)
        val temp = mutableList[i]
        mutableList[i] = mutableList[j]
        mutableList[j] = temp
    }

    return mutableList
}