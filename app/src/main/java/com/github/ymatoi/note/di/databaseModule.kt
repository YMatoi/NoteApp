package com.github.ymatoi.note.di

import androidx.room.Room
import com.github.ymatoi.note.database.Database
import org.koin.dsl.module

val databaseModule = module {
    single { Room.databaseBuilder(get(), Database::class.java, "record-db").build() }
}