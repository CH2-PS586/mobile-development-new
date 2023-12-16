package com.dicoding.elanhakim.storyApp.data.remote.response.file

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("UploadResponse")
	val uploadResponse: List<UploadResponseItem>
)

data class DbRecord(

	@field:SerializedName("filename")
	val filename: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("owner_id")
	val ownerId: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("file_size")
	val fileSize: Int,

	@field:SerializedName("gcp_bucket_url")
	val gcpBucketUrl: String
)

data class UploadResponseItem(

	@field:SerializedName("db_record")
	val dbRecord: DbRecord,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("upload_message")
	val uploadMessage: String,

	@field:SerializedName("category")
	val category: String
)
