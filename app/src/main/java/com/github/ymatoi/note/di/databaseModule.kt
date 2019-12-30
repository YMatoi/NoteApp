package com.github.ymatoi.note.di

import androidx.room.Room
import com.github.ymatoi.note.database.NoteDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { Room.databaseBuilder(get(), NoteDatabase::class.java, "record-db").build() }
}
