package com.dicoding.elanhakim.storyApp.data.remote.response.user

import com.google.gson.annotations.SerializedName
data class User(

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("token_type")
    val token: String,

    @field:SerializedName("isLogin")
    val isLogin: Boolean
)