package com.dicoding.elanhakim.fileManagerAI.view.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.elanhakim.fileManagerAI.data.local.repository.Repository
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User

class WelcomeViewModel constructor(
    private val repository: Repository
    ) : ViewModel() {
    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }
}