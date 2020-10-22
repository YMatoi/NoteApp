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
    @ColumnInfo(name = "text") val text: String? = null,
    @ColumnInfo(name = "recorded_at") val recordedAt: Calendar = Calendar.getInstance(),
    @PrimaryKey val uuid: String = UUID.randomUUID().toString()
) : Parcelable {
    val dateTimeText
        get() = recordedAt.dateTimeText()
}
