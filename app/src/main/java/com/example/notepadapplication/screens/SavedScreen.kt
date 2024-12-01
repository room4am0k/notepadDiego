package com.example.notepadapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.notepadapplication.utils.shareNoteWithFile
import com.example.notepadapplication.viewmodel.NotepadViewModel

@Composable
fun SavedScreenLayout( notepadViewModel: NotepadViewModel){
   val notesItem=notepadViewModel.notes.collectAsState()
    val context= LocalContext.current
    LazyColumn {
        items(notesItem.value.size){index->
            val currentItem=notesItem.value[index]
            NoteItem(title = currentItem.title.toString(), content = currentItem.notes.toString(),
                timeStamp = currentItem.timeStamp.toString(),
                onShareClick = {
                    shareNoteWithFile(context,currentItem.title.toString(), currentItem.notes.toString(),
                        currentItem.timeStamp.toString() )
                }, onDeleteClick = {notepadViewModel.removeNote(currentItem)})
        }
    }
}
@Composable
fun NoteItem(
    title: String,
    content: String,
    timeStamp: String,
    onShareClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(16.dp) // Rounded border
            )
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f) // Makes the column take remaining space
        ) {
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Content
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Timestamp
            Text(
                text = timeStamp,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Icons
        Row(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Share Icon
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Note",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // Delete Icon
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Note",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
