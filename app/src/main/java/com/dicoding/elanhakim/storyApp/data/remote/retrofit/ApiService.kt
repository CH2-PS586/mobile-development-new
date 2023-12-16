package com.dicoding.elanhakim.storyApp.data.remote.retrofit

import com.dicoding.elanhakim.storyApp.data.remote.response.file.UploadResponse
import com.dicoding.elanhakim.storyApp.data.remote.response.file.music.MusicResponse
import com.dicoding.elanhakim.storyApp.data.remote.response.file.music.MusicResponseItem
import com.dicoding.elanhakim.storyApp.data.remote.response.user.LoginResponse
import com.dicoding.elanhakim.storyApp.data.remote.response.user.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("oauth/")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): LoginResponse

    @Multipart
    @POST("files")
    suspend fun upload(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("files") files: RequestBody,
    ):UploadResponse

    @GET("files/music")
    suspend fun getMusic(
        @Header("Authorization") token: String
    ): MusicResponse

}

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
)