package com.github.ymatoi.note.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.ymatoi.note.database.NoteDatabase
import com.github.ymatoi.note.util.combine
import com.github.ymatoi.note.util.copy
import com.github.ymatoi.note.util.dateText
import com.github.ymatoi.note.util.setNextDate
import com.github.ymatoi.note.util.setPrevDate
import com.github.ymatoi.note.util.setZeroTime
import java.util.Calendar
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val database: NoteDatabase by inject()

    val notes = database.noteDao().getAll()
}
