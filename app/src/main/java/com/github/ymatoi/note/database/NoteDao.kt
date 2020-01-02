package com.github.ymatoi.note.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY recorded_at")
    fun getAll(): LiveData<List<Note>>

    @Insert
    suspend fun insert(vararg notes: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}
