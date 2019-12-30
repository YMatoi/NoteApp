package com.github.ymatoi.note.ui

import android.text.format.DateFormat
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.github.ymatoi.note.database.Database
import com.github.ymatoi.note.database.Note
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class EditViewModel : ViewModel(), KoinComponent {
    private val database: Database by inject()
    private val createdAt = MutableLiveData<Calendar>(Calendar.getInstance())
    val date = Transformations.map(createdAt) { DateFormat.format("yyyy/MM/dd (E) kk:mm:ss", it)}
    val text = MutableLiveData<String>()

    fun saveNote(view: View) {
        val createdAt = createdAt.value ?: return
        val text = text.value ?: return
        val note = Note(

            createdAt = createdAt,
            text = text
        )
        viewModelScope.launch {
            database.noteDao().insert(note)
            Navigation.findNavController(view).popBackStack()
        }
    }
}
