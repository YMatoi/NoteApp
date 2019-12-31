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
    val date = Transformations.map(recordedAt) { DateFormat.format("yyyy/MM/dd (E)", it) }
    val time = Transformations.map(recordedAt) { DateFormat.format("kk:mm", it) }
    val text = MutableLiveData<String>()

    fun saveNote(view: View) {
        val recordedAt = recordedAt.value ?: return
        val text = text.value ?: return
        val note = Note(
            recordedAt = recordedAt,
            text = text
        )
        viewModelScope.launch {
            database.noteDao().insert(note)
            Navigation.findNavController(view).popBackStack()
        }
    }
}
