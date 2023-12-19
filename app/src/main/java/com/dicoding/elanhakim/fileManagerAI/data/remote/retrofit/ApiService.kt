package com.dicoding.elanhakim.fileManagerAI.data.remote.retrofit

import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.UploadResponse
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.UploadResponseItem
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.document.DocumentResponse
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.music.MusicResponseItem
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.others.OthersResponse
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.picture.PictureResponse
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.video.VideoResponse
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.LoginResponse
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.RegisterResponse
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
    ): List<UploadResponseItem>

    @GET("files/music")
    suspend fun getMusic(
        @Header("Authorization") token: String
    ): List<MusicResponseItem>

    @GET("files/video")
    suspend fun getVideo(
        @Header("Authorization") token: String
    ): List<VideoResponse>

    @GET("files/others")
    suspend fun getOthers(
        @Header("Authorization") token: String
    ): List<OthersResponse>

    @GET("files/document/Personal")
    suspend fun getPersonal(
        @Header("Authorization") token: String
    ): List<DocumentResponse>

    @GET("files/document/School")
    suspend fun getSchool(
        @Header("Authorization") token: String
    ): List<DocumentResponse>

    @GET("files/picture/Collage")
    suspend fun getCollage(
        @Header("Authorization") token: String
    ): List<PictureResponse>

    @GET("files/picture/Food")
    suspend fun getFood(
        @Header("Authorization") token: String
    ): List<PictureResponse>

    @GET("files/picture/Friends")
    suspend fun getFriends(
        @Header("Authorization") token: String
    ): List<PictureResponse>

    @GET("files/picture/Memes")
    suspend fun getMemes(
        @Header("Authorization") token: String
    ): List<PictureResponse>

    @GET("files/picture/Pets")
    suspend fun getPets(
        @Header("Authorization") token: String
    ): List<PictureResponse>

    @GET("files/picture/Selfie")
    suspend fun getSelfie(
        @Header("Authorization") token: String
    ): List<PictureResponse>

}

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
)