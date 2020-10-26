package com.github.ymatoi.note.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.database.NoteDatabase
import com.github.ymatoi.note.util.combine
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
            val notes = it.toObjects(Note::class.java).sortedBy { it.recordedAt }.reversed()
            _notes.postValue(notes)
        }
    }
    fun getAll() = when (currentUser == null) {
        true -> database.noteDao().getAll()
        false -> getAllFromFirestore()
    }

    private val _query = MutableLiveData<String>()
    private val _searchedText = combine(_query, _notes) { query, notes ->
        notes?.filter { when (query) {
            null -> true
            else -> it.text.contains(Regex.fromLiteral(query))
        } }?.sortedBy { it.recordedAt }?.reversed()
    }
    private fun findByTextFirestore(query: String) = _searchedText.also {
        _query.postValue(query)
    }
    fun findByText(query: String) = when (currentUser == null) {
        true -> database.noteDao().findByText(query)
        false -> findByTextFirestore(query)
    }

    suspend fun insert(note: Note) {
        when (currentUser == null) {
            true -> database.noteDao().insert(note)
            false -> collection?.document(note.uuid)?.set(note)
        }
    }
    suspend fun update(note: Note) {
        when (currentUser == null) {
            true -> database.noteDao().update(note)
            false -> collection?.document(note.uuid)?.set(note)
        }
    }
    suspend fun delete(note: Note) {
        when (currentUser == null) {
            true -> database.noteDao().delete(note)
            false -> collection?.document(note.uuid)?.delete()
        }
    }
}
