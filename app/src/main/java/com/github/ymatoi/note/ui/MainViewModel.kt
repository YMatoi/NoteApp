package com.github.ymatoi.note.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.ymatoi.note.database.NoteDatabase

class MainViewModel @ViewModelInject constructor(
    private val database: NoteDatabase
) : ViewModel() {
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
