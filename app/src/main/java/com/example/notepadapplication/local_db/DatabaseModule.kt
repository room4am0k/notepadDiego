package com.example.notepadapplication.local_db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotepadDatabase {
        return NotepadDatabase.getDatabase(context)
    }

    @Provides
    fun provideNotepadDao(database: NotepadDatabase): NotepadDao {
        return database.notepadDao()
    }
}
