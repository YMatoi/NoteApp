package com.github.ymatoi.note.di

import android.content.Context
import androidx.room.Room
import com.github.ymatoi.note.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideNoteDB(@ApplicationContext context: Context) = Room.databaseBuilder(context, NoteDatabase::class.java, "record-db").build()
}
