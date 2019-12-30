package com.github.ymatoi.note.database

import androidx.room.TypeConverter
import java.util.*

class CalendarConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? {
        return if (value == null) null else Calendar.getInstance().apply {
            timeInMillis = value
        }
    }

    @TypeConverter
    fun toTimestamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }
}