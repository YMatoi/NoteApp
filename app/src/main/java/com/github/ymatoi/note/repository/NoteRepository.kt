package com.github.ymatoi.note.repository

import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.database.NoteDatabase
import com.github.ymatoi.note.firestore.NoteFirestoreDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val database: NoteDatabase,
    private val firestore: NoteFirestoreDao
) {
    private val currentUser get() = Firebase.auth.currentUser

    fun getAll() = when (currentUser == null) {
        true -> database.noteDao().getAll()
        false -> firestore.getAll()
    }

    fun findByText(query: String) = when (currentUser == null) {
        true -> database.noteDao().findByText(query)
        false -> firestore.findByText(query)
    }

    suspend fun insert(note: Note) {
        when (currentUser == null) {
            true -> database.noteDao().insert(note)
            false -> firestore.insert(note)
        }
    }
    suspend fun update(note: Note) {
        when (currentUser == null) {
            true -> database.noteDao().update(note)
            false -> firestore.update(note)
        }
    }
    suspend fun delete(note: Note) {
        when (currentUser == null) {
            true -> database.noteDao().delete(note)
            false -> firestore.delete(note)
        }
    }
}
