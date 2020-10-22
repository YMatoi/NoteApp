package com.github.ymatoi.note.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.database.NoteDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val database: NoteDatabase
) {
    private val currentUser get() = Firebase.auth.currentUser
    private val collection get() = currentUser?.let { FirebaseFirestore.getInstance().collection("/users/${it.uid}/notes") }

    private val _notes = MutableLiveData<List<Note>>()
    private fun getAllFromFirestore(): LiveData<List<Note>> = _notes.also {
        collection?.get()?.addOnSuccessListener {
            _notes.postValue(it.toObjects(Note::class.java))
        }
    }
    fun getAll() = when (currentUser == null) {
        true -> database.noteDao().getAll()
        false -> getAllFromFirestore()
    }

    fun findByText(query: String) = database.noteDao().findByText(query)
    suspend fun insert(note: Note) = database.noteDao().insert(note).also {
        collection?.document(note.uuid)?.set(note)
    }
    suspend fun update(note: Note) = database.noteDao().update(note).also {
        collection?.document(note.uuid)?.set(note)
    }
    suspend fun delete(note: Note) = database.noteDao().delete(note).also {
        collection?.document(note.uuid)?.delete()
    }
}
