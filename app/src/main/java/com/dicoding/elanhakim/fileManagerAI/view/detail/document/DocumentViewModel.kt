package com.dicoding.elanhakim.fileManagerAI.view.detail.document

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.elanhakim.fileManagerAI.data.local.repository.Repository
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User

class DocumentViewModel(
    private val repository: Repository
)
    : ViewModel(){

    fun getSessionData(): LiveData<User> =
        repository.getSession().asLiveData()

    fun getPersonal(token: String) = repository.getPersonal(token)

    fun getSchool(token: String) = repository.getSchool(token)

}