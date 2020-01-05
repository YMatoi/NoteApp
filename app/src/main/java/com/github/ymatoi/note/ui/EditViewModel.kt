package com.github.ymatoi.note.ui

import android.text.format.DateFormat
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.database.NoteDatabase
import java.util.Calendar
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class EditViewModel : ViewModel(), KoinComponent {
    private val database: NoteDatabase by inject()
    private val recordedAt = MutableLiveData<Calendar>(Calendar.getInstance())
    fun getRecordedAt() = recordedAt.value ?: Calendar.getInstance()
    val date = Transformations.map(recordedAt) { DateFormat.format("yyyy/MM/dd (E)", it) }
    val time = Transformations.map(recordedAt) { DateFormat.format("kk:mm", it) }
    val text = MutableLiveData<String>()

    private val note = MutableLiveData<Note>()
    val deleteButtonVisibility = Transformations.map(note) {
        when (it != null) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }
    private fun getNote() = note.value

    fun saveNote(view: View) {
        val recordedAt = recordedAt.value ?: return
        val text = text.value ?: return
        val note = Note(
            id = getNote()?.id,
            recordedAt = recordedAt,
            text = text
        )
        viewModelScope.launch {
            if (note.id == null) {
                database.noteDao().insert(note)
            } else {
                database.noteDao().update(note)
            }
            Navigation.findNavController(view).popBackStack()
        }
    }

    fun editMode(note: Note?) {
        note ?: return
        recordedAt.postValue(note.recordedAt)
        text.postValue(note.text)
        this.note.postValue(note)
    }

    fun deleteNote(view: View) {
        val note = getNote() ?: return
        viewModelScope.launch {
            database.noteDao().delete(note)
            Navigation.findNavController(view).popBackStack()
        }
    }

    fun setDate(year: Int, month: Int, date: Int) {
        val next = getRecordedAt().apply {
            set(year, month, date)
        }
        if (next != null) recordedAt.postValue(next)
    }
    fun setTime(hourOfDay: Int, minute: Int) {
        val next = getRecordedAt().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        if (next != null) recordedAt.postValue(next)
    }
}
