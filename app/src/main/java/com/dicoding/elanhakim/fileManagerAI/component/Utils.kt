package com.dicoding.elanhakim.fileManagerAI.component

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale

fun uriToFile(fileUri: Uri, context: Context): File? {
    try {
        val inputStream = context.contentResolver.openInputStream(fileUri)
        inputStream?.use { input ->
            val fileName = getFileNameFromUri(fileUri, context)
            val fileExtension = getFileExtension(fileName)
            val myFile = createCustomTempFile(context, fileName, fileExtension)
            val outputStream = FileOutputStream(myFile)
            outputStream.use { output ->
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } > 0) {
                    output.write(buffer, 0, bytesRead)
                }
                return myFile
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}

private fun getFileExtension(fileName: String): String {
    val dotIndex = fileName.lastIndexOf('.')
    return if (dotIndex == -1 || dotIndex >= fileName.length - 1) {
        "txt" // Default extension if not found or if the dot is at the end
    } else {
        fileName.substring(dotIndex + 1).toLowerCase(Locale.ROOT)
    }
}

private fun createCustomTempFile(context: Context, fileName: String, fileExtension: String): File {
    // Implement your logic to create a custom temp file
    // For example, you can create a file in the cache directory
    val cacheDir = context.cacheDir
    return File.createTempFile(fileName, ".$fileExtension", cacheDir)
}

private fun getFileNameFromUri(uri: Uri, context: Context): String {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        return it.getString(nameIndex)
    }
    return "unknown_file"
}

private const val DEFAULT_BUFFER_SIZE = 1024 * 4