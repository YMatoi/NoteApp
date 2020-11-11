package com.github.ymatoi.note.ui.edit

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.repository.NoteRepository
import com.github.ymatoi.note.util.dateText
import com.github.ymatoi.note.util.timeText
import java.util.Calendar
import kotlinx.coroutines.launch

class EditViewModel @ViewModelInject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val recordedAt = MutableLiveData(Calendar.getInstance())
    fun getRecordedAt(): Calendar = recordedAt.value ?: Calendar.getInstance()
    val date = Transformations.map(recordedAt) { it.dateText() }
    val time = Transformations.map(recordedAt) { it.timeText() }
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

        viewModelScope.launch {
            val uuid = getNote()?.uuid
            if (uuid == null) {
                noteRepository.insert(Note(text, recordedAt))
            } else {
                noteRepository.update(Note(text, recordedAt, uuid))
            }
            Navigation.findNavController(view).popBackStack()
        }
    }

    fun editMode(note: Note?) {
        note ?: return
        recordedAt.postValue(note.recordedAt)
        text.postValue(note.text)
    }

    fun deleteNote(view: View) {
        val note = getNote() ?: return
        viewModelScope.launch {
            noteRepository.delete(note)
            Navigation.findNavController(view).popBackStack()
        }
    }

    fun setDate(year: Int, month: Int, date: Int) {
        val next = getRecordedAt().apply {
            set(year, month, date)
        }
        recordedAt.postValue(next)
    }
    fun setTime(hourOfDay: Int, minute: Int) {
        val next = getRecordedAt().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        recordedAt.postValue(next)
    }

    private val _softwareKeyboardVisibility = MutableLiveData(false)
    fun setSoftwareKeyboardVisibility(visibility: Boolean) = _softwareKeyboardVisibility.postValue(visibility)
    val datetimeVisibility = Transformations.map(_softwareKeyboardVisibility) {
        when (it) {
            true -> View.GONE
            else -> View.VISIBLE
        }
    }
}
