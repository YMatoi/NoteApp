package com.github.ymatoi.note.util

import android.text.format.DateFormat
import java.util.Calendar

fun Calendar.dateText() = DateFormat.format("yyyy/MM/dd (E)", this).toString()
fun Calendar.dateTimeText() = DateFormat.format("yyyy/MM/dd (E) kk:mm", this).toString()
fun Calendar.timeText() = DateFormat.format("kk:mm", this).toString()
