package com.dicoding.elanhakim.fileManagerAI.view.download

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.elanhakim.fileManagerAI.data.local.repository.Repository
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User
class DownloadViewModel(
    private val repository: Repository
)
    : ViewModel(){

    fun getSessionData(): LiveData<User> =
        repository.getSession().asLiveData()

    fun downloadFile(token: String, category: String, filename: String) = repository.downloadNoLabel(token, category, filename)

    fun downloadFile(token: String, category: String, label:String, filename: String) = repository.downloadWithLabel(token, category, label, filename)
}