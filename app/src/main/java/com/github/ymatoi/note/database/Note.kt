package com.github.ymatoi.note.database

import android.os.Parcelable
import android.text.format.DateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Note(
    @ColumnInfo(name = "text") var text: String?,
    @ColumnInfo(name = "recorded_at") var recordedAt: Calendar,
    @PrimaryKey(autoGenerate = true) var id: Int? = null
) : Parcelable {
    val dateTimeText
        get() = DateFormat.format("yyyy/MM/dd (E) kk:mm", recordedAt).toString()

    val textNote
        get() = "> %s \n %s".format(dateTimeText, text)
}
