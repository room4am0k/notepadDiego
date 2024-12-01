package com.example.notepadapplication.local_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notepadapplication.model.NotepadEntity

@Database(entities = [NotepadEntity::class], version = 2, exportSchema = false)
abstract class NotepadDatabase : RoomDatabase() {
    abstract fun notepadDao(): NotepadDao

    companion object {
        @Volatile
        private var INSTANCE: NotepadDatabase? = null


        fun getDatabase(context: Context): NotepadDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotepadDatabase::class.java,
                    "notepad_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}