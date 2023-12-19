package com.dicoding.elanhakim.fileManagerAI.data.local.repository

import com.dicoding.elanhakim.fileManagerAI.data.remote.retrofit.ApiService
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import androidx.lifecycle.liveData
import com.dicoding.elanhakim.fileManagerAI.data.local.pref.UserPreference
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User
import com.dicoding.elanhakim.fileManagerAI.data.remote.retrofit.RegisterRequest
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

    fun uploadFile(file: File, description: String, token: String) = liveData {
        emit(ResultApi.Loading)

        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestFile = file.asRequestBody()

        val multiPartBody = MultipartBody.Part.createFormData(
            "files",
            file.name,
            requestFile
        )

        try {
            val successResponse = apiService.upload("Bearer $token", multiPartBody, requestBody)
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun getMusic(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getMusic("Bearer $token")
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun getVideo(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getVideo("Bearer $token")
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun getOthers(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getOthers("Bearer $token")
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun getPersonal(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getPersonal("Bearer $token")
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun getSchool(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getSchool("Bearer $token")
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun getCollage(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getCollage("Bearer $token")
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun getFood(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getFood("Bearer $token")
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun getFriends(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getFriends("Bearer $token")
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun getMemes(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getMemes("Bearer $token")
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun getPets(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getPets("Bearer $token")
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

    fun getSelfie(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getSelfie("Bearer $token")
            emit(ResultApi.Success(successResponse))
        } catch (e: HttpException) {
            emit(ResultApi.Error(e.message()))
        }
    }

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