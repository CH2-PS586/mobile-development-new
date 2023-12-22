package com.dicoding.elanhakim.fileManagerAI.data.local.repository

import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.webkit.MimeTypeMap
import com.dicoding.elanhakim.fileManagerAI.data.remote.retrofit.ApiService
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import androidx.lifecycle.liveData
import com.dicoding.elanhakim.fileManagerAI.data.local.pref.UserPreference
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User
import com.dicoding.elanhakim.fileManagerAI.data.remote.retrofit.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream

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
        val fileMediaType = file.guessMediaType().toMediaType()
        val requestFile = file.asRequestBody(fileMediaType)

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

    private fun File.guessMediaType(): String {
        val extension = MimeTypeMap.getFileExtensionFromUrl(absolutePath)
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension) ?: "application/octet-stream"
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

    fun getWork(token: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val successResponse = apiService.getWork("Bearer $token")
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

    fun downloadNoLabel(token: String, category: String, filename: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val response = apiService.downloadNoLabel("Bearer $token", category, filename)
            emit(ResultApi.Success(response))
            if (response.isSuccessful) {
                // Get the file name from the URL or use a custom name
                withContext(Dispatchers.IO) {
                    val file = File(
                        getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        filename
                    )

                    // Save the file
                    response.body()?.let { responseBody ->
                        val outputStream = FileOutputStream(file)
                        val inputStream = responseBody.byteStream()
                        val buffer = ByteArray(4096)
                        var bytesRead: Int

                        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                            outputStream.write(buffer, 0, bytesRead)
                        }

                        outputStream.close()
                        inputStream.close()
                    }

                    // File downloaded successfully
                    println("File downloaded successfully: $file")
                    emit(ResultApi.Success(response))
                }
            } else {
                // Handle unsuccessful response
                println("Failed to download file. Response code: ${response.code()}")
            }
        } catch (e: Exception) {
            // Handle exceptions
            println("Error downloading file: ${e.message}")
        }
    }

    fun downloadWithLabel(token: String, category: String, label: String, filename: String) = liveData {
        emit(ResultApi.Loading)
        try {
            val response = apiService.downloadWithLabel("Bearer $token", category, label, filename)
            emit(ResultApi.Success(response))
            if (response.isSuccessful) {
                // Get the file name from the URL or use a custom name
                withContext(Dispatchers.IO) {
                    val file = File(
                        getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        filename
                    )

                    // Save the file
                    response.body()?.let { responseBody ->
                        val outputStream = FileOutputStream(file)
                        val inputStream = responseBody.byteStream()
                        val buffer = ByteArray(4096)
                        var bytesRead: Int

                        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                            outputStream.write(buffer, 0, bytesRead)
                        }

                        outputStream.close()
                        inputStream.close()
                    }

                    // File downloaded successfully
                    println("File downloaded successfully: $file")
                    emit(ResultApi.Success(response))
                }
            } else {
                // Handle unsuccessful response
                println("Failed to download file. Response code: ${response.code()}")
            }
        } catch (e: Exception) {
            // Handle exceptions
            println("Error downloading file: ${e.message}")
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