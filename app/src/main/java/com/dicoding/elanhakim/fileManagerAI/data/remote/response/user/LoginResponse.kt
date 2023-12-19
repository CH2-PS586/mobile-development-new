package com.dicoding.elanhakim.fileManagerAI.data.remote.response.user
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("result")
    val loginResult: User,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("massage")
    val message: String
)