package com.github.ymatoi.note.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Note::class], version = 1)
@TypeConverters(CalendarConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
