package com.example.notepadapplication.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.notepadapplication.model.NotepadEntity
import com.example.notepadapplication.utils.getCurrentDateTimeFormatted
import com.example.notepadapplication.viewmodel.NotepadViewModel

@Composable
fun HomeScreenLayout(notepadViewModel: NotepadViewModel){
    // State variables for user input
    var title by remember { mutableStateOf("") }
    var noteContent by remember { mutableStateOf("") }
val context= LocalContext.current
    // Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title TextField
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true,
            shape = RoundedCornerShape(16.dp) // Rounded corners
        )

        // Note Content TextField
        OutlinedTextField(
            value = noteContent,
            onValueChange = { noteContent = it },
            label = { Text("Write your note here") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Expands to take remaining space
            maxLines = Int.MAX_VALUE,
            shape = RoundedCornerShape(16.dp) // Rounded corners
        )
        Button(onClick = {
            val notepadData= NotepadEntity(title = title, notes = noteContent, timeStamp = getCurrentDateTimeFormatted())

            if(title.isEmpty() || noteContent.isEmpty()){
                Toast.makeText(context,"None of the field can be empty",Toast.LENGTH_SHORT).show()
            }
            else{
                notepadViewModel.addNote(notepadData)
                Toast.makeText(context,"Saved",Toast.LENGTH_SHORT).show()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Save Notes")
        }
    }
}