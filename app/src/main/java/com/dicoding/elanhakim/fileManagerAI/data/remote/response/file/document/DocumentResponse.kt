package com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.document

import com.google.gson.annotations.SerializedName

data class DocumentResponse(

    @field:SerializedName("filename")
    val filename: String,

    @field:SerializedName("file_size")
    val fileSize: String,

    @field:SerializedName("gcs_url")
    val gcsUrl: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

)
