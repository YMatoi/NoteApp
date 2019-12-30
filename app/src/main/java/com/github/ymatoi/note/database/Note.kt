package com.github.ymatoi.note.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity
data class Note(
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "created_at") val createdAt: Calendar
) {
    @PrimaryKey(autoGenerate = true) var id: Int? = null
}
