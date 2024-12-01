package com.example.notepadapplication.local_db
import androidx.room.*
import com.example.notepadapplication.model.NotepadEntity

@Dao
interface NotepadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NotepadEntity)

    @Delete
    suspend fun deleteNote(note: NotepadEntity)

    @Query("SELECT * FROM notepad_table ORDER BY timeStamp DESC")
    suspend fun getAllNotes(): List<NotepadEntity>
}