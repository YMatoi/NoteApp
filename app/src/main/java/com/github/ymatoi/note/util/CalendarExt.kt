package com.github.ymatoi.note.util

import java.util.Calendar

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
