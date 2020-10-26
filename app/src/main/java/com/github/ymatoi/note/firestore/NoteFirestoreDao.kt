package com.github.ymatoi.note.firestore

import androidx.lifecycle.MutableLiveData
import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.util.combine
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class NoteFirestoreDao @Inject constructor() {
    private val currentUser get() = Firebase.auth.currentUser
    private val collection get() = currentUser?.let { FirebaseFirestore.getInstance().collection("/users/${it.uid}/notes") }

    private val _notes = MutableLiveData<List<Note>>()
    fun getAll() = _notes.also {
        collection?.get()?.addOnSuccessListener {
            val notes = it.toObjects(Note::class.java).sortedBy { it.recordedAt }.reversed()
            _notes.postValue(notes)
        }
    }

    private val _query = MutableLiveData<String>()
    private val _searchedText = combine(_query, _notes) { query, notes ->
        notes?.filter { when (query) {
            null -> true
            else -> it.text.contains(Regex.fromLiteral(query))
        } }?.sortedBy { it.recordedAt }?.reversed()
    }
    fun findByText(query: String) = _searchedText.also {
        _query.postValue(query)
    }

    fun insert(note: Note) = collection?.document(note.uuid)?.set(note)
    fun update(note: Note) = collection?.document(note.uuid)?.set(note)
    fun delete(note: Note) = collection?.document(note.uuid)?.delete()
}
