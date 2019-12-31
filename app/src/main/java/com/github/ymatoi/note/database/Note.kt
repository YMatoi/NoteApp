package com.github.ymatoi.note.database

import android.text.format.DateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity
data class Note(
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "recorded_at") val recordedAt: Calendar
) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null

    val dateText
        get() = DateFormat.format("yyyy/MM/dd (E) kk:mm:ss", recordedAt).toString()
}
