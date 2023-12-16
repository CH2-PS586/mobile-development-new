package com.dicoding.elanhakim.storyApp.data.local.repository

import com.dicoding.elanhakim.storyApp.data.remote.retrofit.ApiService
import com.dicoding.elanhakim.storyApp.data.remote.response.ResultApi
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.dicoding.elanhakim.storyApp.data.local.pref.UserPreference
import com.dicoding.elanhakim.storyApp.data.remote.response.file.music.MusicResponseItem
import com.dicoding.elanhakim.storyApp.data.remote.response.user.LoginResponse
import com.dicoding.elanhakim.storyApp.data.remote.response.user.User
import com.dicoding.elanhakim.storyApp.data.remote.retrofit.RegisterRequest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class Repository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
){

    fun login(username: String, password: String) = liveData {
        emit(ResultApi.Loading)

        try {
            val successResponse = apiService.login(username, password)
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun signUp(request: RegisterRequest) = liveData {
        emit(ResultApi.Loading)

        try {
            val successResponse = apiService.register(request)
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun getMusic(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getMusic("Bearer$token")
            emit(ResultApi.Success(successResponse.musicResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

//    fun uploadFile(imageFile: File, token: String) = liveData {
//        emit(ResultApi.Loading)
//
//        val requestImageFile = imageFile.asRequestBody()
//
//        val multiPartBody = MultipartBody.Part.createFormData(
//            "photo",
//            imageFile.name,
//            requestImageFile
//        )
//
//        try {
//            val successResponse = apiService.upload("Bearer $token", multiPartBody)
//            emit(ResultApi.Success(successResponse))
//        } catch (e: HttpException) {
//            emit(ResultApi.Error(e.message()))
//        }
//    }

    suspend fun saveSession(user: User) {
        userPreference.saveSession(user)
    }

    fun getSession() = userPreference.getSession()

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        private var instance: Repository? = null
        fun getInstance(apiService: ApiService, pref: UserPreference): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, pref)
            }.also { instance = it }
    }
}