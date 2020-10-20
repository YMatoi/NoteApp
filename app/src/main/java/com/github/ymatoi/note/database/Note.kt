package com.github.ymatoi.note.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.ymatoi.note.util.dateTimeText
import java.util.Calendar
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Note(
    @ColumnInfo(name = "text") var text: String? = null,
    @ColumnInfo(name = "recorded_at") var recordedAt: Calendar = Calendar.getInstance(),
    @PrimaryKey(autoGenerate = true) var id: Int? = null
) : Parcelable {
    val dateTimeText
        get() = recordedAt.dateTimeText()
}
