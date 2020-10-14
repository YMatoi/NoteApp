package com.github.ymatoi.note.repository

import com.github.ymatoi.note.database.Note
import com.github.ymatoi.note.database.NoteDatabase
import java.util.Calendar
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val database: NoteDatabase
) {
    fun getAll() = database.noteDao().getAll()
    fun findByText(query: String) = database.noteDao().findByText(query)
    fun get(from: Calendar, to: Calendar) = database.noteDao().get(from, to)
    suspend fun insert(note: Note) = database.noteDao().insert(note)
    suspend fun update(note: Note) = database.noteDao().update(note)
    suspend fun delete(note: Note) = database.noteDao().delete(note)
}
