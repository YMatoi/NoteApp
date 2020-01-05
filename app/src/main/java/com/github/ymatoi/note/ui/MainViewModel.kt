package com.github.ymatoi.note.ui

import android.text.format.DateFormat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.ymatoi.note.database.NoteDatabase
import com.github.ymatoi.note.util.combine
import com.github.ymatoi.note.util.copy
import com.github.ymatoi.note.util.setNextDate
import com.github.ymatoi.note.util.setPrevDate
import com.github.ymatoi.note.util.setZeroTime
import java.util.Calendar
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val database: NoteDatabase by inject()

    private val calendar = MutableLiveData<Calendar>(Calendar.getInstance())

    private val dateText = Transformations.map(calendar) {
        DateFormat.format("yyyy/MM/dd (E)", it).toString()
    }

    private val notes = Transformations.switchMap(calendar) {
        val from = it.copy().apply {
            setZeroTime()
        }
        val to = it.copy().apply {
            setZeroTime()
            setNextDate()
        }
        database.noteDao().get(from, to)
    }

    val data = combine(dateText, notes) { dateText, notes ->
        Pair(dateText, notes)
    }

    fun getDateText(): String? = dateText.value
    fun getTextNotes(): String? = notes.value?.map { it.textNote }?.joinToString("\n\n")

    fun next() = calendar.postValue(
        calendar.value?.apply {
            setNextDate()
        }
    )

    fun prev() = calendar.postValue(
        calendar.value?.apply {
            setPrevDate()
        }
    )
}
