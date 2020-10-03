package com.github.ymatoi.note.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.ymatoi.note.database.NoteDatabase
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val database: NoteDatabase by inject()

    private val searchKeyword = MutableLiveData<String?>("")
    fun setQuery(query: String?) = searchKeyword.postValue(query ?: "")

    val notes = Transformations.switchMap(searchKeyword) {
        if (it.isNullOrBlank()) {
            database.noteDao().getAll()
        } else {
            database.noteDao().findByText(it)
        }
    }
}
