package com.github.ymatoi.note.util

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Calendar.setZeroTime() {
    set(Calendar.HOUR, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
}

fun Calendar.setNextDate() {
    add(Calendar.DATE, 1)
}

fun Calendar.setPrevDate() {
    add(Calendar.DATE, -1)
}

fun Calendar.copy() = this.clone() as Calendar

fun Calendar.dateText() = DateFormat.format("yyyy/MM/dd (E)", this).toString()
fun Calendar.parseFromDateText(text: String): Calendar {
    runCatching {
        time = SimpleDateFormat("yyyy/MM/dd (E)", Locale.JAPAN).parse(text)!!
    }.onFailure {
    }
    return this
}
fun Calendar.dateTimeText() = DateFormat.format("yyyy/MM/dd (E) kk:mm", this).toString()
fun Calendar.timeText() = DateFormat.format("kk:mm", this).toString()
