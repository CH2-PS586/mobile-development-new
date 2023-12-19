package com.dicoding.elanhakim.fileManagerAI.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.elanhakim.fileManagerAI.data.local.repository.Repository
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: Repository
    ) : ViewModel() {

    fun login(username: String, password: String) = repository.login(username,password)

    fun saveSession(user: User) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

}