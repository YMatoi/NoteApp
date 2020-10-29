package com.github.ymatoi.note.repository

import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.database.NoteDao
import com.github.ymatoi.note.database.NoteDatabase
import com.github.ymatoi.note.firestore.NoteFirestoreDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val database: NoteDatabase,
    private val firestore: NoteFirestoreDao
) {
    private val dao: NoteDao get() = when (Firebase.auth.currentUser == null) {
        true -> database.noteDao()
        false -> firestore
    }

    fun getAll() = dao.getAll()
    fun findByText(query: String) = dao.findByText(query)
    suspend fun insert(note: Note) = dao.insert(note)
    suspend fun update(note: Note) = dao.update(note)
    suspend fun delete(note: Note) = dao.delete(note)
}
