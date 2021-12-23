package com.github.welblade.workoutapp.core.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = "dd/MM/yyyy", locale: Locale = Locale.getDefault()): String {
    return SimpleDateFormat(pattern, locale).format(this)
}

fun Date.getDateOnly(locale: Locale = Locale.getDefault()): Date {
    val formatter = SimpleDateFormat("dd/MM/yyyy", locale)
    return formatter.parse(formatter.format(this))!!
}

fun Date.of(year:Int, month: Int, dayOfMonth: Int): Date {
    val cal = Calendar.getInstance()
    cal[Calendar.YEAR] = year
    cal[Calendar.MONTH] = month
    cal[Calendar.DAY_OF_MONTH] = dayOfMonth
    cal[Calendar.HOUR_OF_DAY] = 0
    cal[Calendar.MINUTE] = 0
    cal[Calendar.SECOND] = 0
    cal[Calendar.MILLISECOND] = 0
    return cal.time
}