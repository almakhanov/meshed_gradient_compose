package com.example.meshed_gradient


data class GSCalendarDayRow(
    val startIndex: Int,
    val size: Int,
    val done: Boolean = false,
    val current: Boolean = false,
    val index: Int = 0
)
