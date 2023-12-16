package com.dicoding.elanhakim.storyApp.view.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.elanhakim.storyApp.data.local.repository.Repository
import com.dicoding.elanhakim.storyApp.data.remote.response.user.User
import java.io.File

class UploadViewModel constructor(
    private val repository: Repository
) : ViewModel() {

    fun getSessionData(): LiveData<User> =
        repository.getSession().asLiveData()
//    fun uploadFile(file:File, token: String) =
//        repository.uploadFile(file,token)
}