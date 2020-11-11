package com.github.ymatoi.note.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.ymatoi.note.repository.NoteRepository

class MainViewModel @ViewModelInject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {
    private val searchKeyword = MutableLiveData<String?>("")
    fun setQuery(query: String?) = searchKeyword.postValue(query ?: "")

    val notes = Transformations.switchMap(searchKeyword) {
        if (it.isNullOrBlank()) {
            noteRepository.getAll()
        } else {
            noteRepository.findByText(it)
        }
    }
}
