package com.github.ymatoi.note.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.ymatoi.note.util.dateTimeText
import java.util.Calendar
import java.util.UUID
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Note(
    @ColumnInfo(name = "text") val text: String = "",
    @ColumnInfo(name = "recorded_at") val _recordedAt: Long = Calendar.getInstance().timeInMillis,
    @PrimaryKey val uuid: String = UUID.randomUUID().toString()
) : Parcelable {
    constructor(text: String, recordedAt: Calendar, uuid: String = UUID.randomUUID().toString()) : this(text, recordedAt.timeInMillis, uuid)

    val recordedAt: Calendar get() = Calendar.getInstance().apply {
        timeInMillis = _recordedAt
    }
    val dateTimeText
        get() = recordedAt.dateTimeText()
}
