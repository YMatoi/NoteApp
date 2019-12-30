package com.github.ymatoi.note.ui

import androidx.lifecycle.ViewModel
import com.github.ymatoi.note.database.NoteDatabase
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val database: NoteDatabase by inject()

    val notes = database.noteDao().getAll()
}
