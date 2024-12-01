package com.example.notepadapplication.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentDateTimeFormatted(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date())
}

fun shareNoteWithFile(
    context: Context,
    title: String,
    content: String,
    timeStamp: String
) {
    // Step 1: Create the file with title as the name
    val fileName = "$title.txt"
    val fileContent = """
        Title: $title
        
        Content: $content
        
        Timestamp: $timeStamp
    """.trimIndent()

    // Step 2: Get the directory for saving the file
    val fileDirectory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    val file = File(fileDirectory, fileName)

    try {
        // Step 3: Write the content to the file
        FileOutputStream(file).use { outputStream ->
            outputStream.write(fileContent.toByteArray())
        }

        // Step 4: Use FileProvider to get a content URI for the file
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "com.example.notepadapplication.fileprovider", // Replace with your actual provider authority
            file
        )

        // Step 5: Create an Intent to share the file
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain" // You can use "text/plain" for sharing as text or "application/octet-stream" for file sharing
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Note: $title")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant permission to read the URI
        }

        // Step 6: Start the share Intent
        context.startActivity(Intent.createChooser(shareIntent, "Share Note"))

    } catch (e: IOException) {
        e.printStackTrace()
    }
}