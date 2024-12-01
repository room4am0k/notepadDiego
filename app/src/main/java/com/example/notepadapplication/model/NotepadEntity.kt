package com.example.notepadapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notepad_table")
data class NotepadEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title:String?=null,
    val notes: String? = null,
    val timeStamp: String? = null
)