package com.dicoding.elanhakim.fileManagerAI.data.remote.response.user

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("massage")
    val message: String
)