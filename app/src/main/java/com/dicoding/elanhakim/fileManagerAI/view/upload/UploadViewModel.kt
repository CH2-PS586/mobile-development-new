package com.dicoding.elanhakim.fileManagerAI.view.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.elanhakim.fileManagerAI.data.local.repository.Repository
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User
import java.io.File

class UploadViewModel constructor(
    private val repository: Repository
) : ViewModel() {

    fun getSessionData(): LiveData<User> =
        repository.getSession().asLiveData()
    fun uploadFile(file: File, description: String, token: String) =
        repository.uploadFile(file, description, token)
}