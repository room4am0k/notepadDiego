package com.example.notepadapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notepadapplication.local_db.NotepadDao
import com.example.notepadapplication.model.NotepadEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotepadViewModel @Inject constructor(private val notepadDao: NotepadDao) : ViewModel() {

    // StateFlow to hold the list of notes
    private val _notes = MutableStateFlow<List<NotepadEntity>>(emptyList())
    val notes: StateFlow<List<NotepadEntity>> get() = _notes
init {
    fetchAllNotes()
}
    // Function to fetch all notes
    private fun fetchAllNotes() {
        viewModelScope.launch {
            _notes.value = notepadDao.getAllNotes()
        }
    }

    // Function to insert a note
    fun addNote(note: NotepadEntity) {
        viewModelScope.launch {
            notepadDao.insertNote(note)
            fetchAllNotes() // Refresh the list
        }
    }

    // Function to delete a note
    fun removeNote(note: NotepadEntity) {
        viewModelScope.launch {
            notepadDao.deleteNote(note)
            fetchAllNotes() // Refresh the list
        }
    }
}