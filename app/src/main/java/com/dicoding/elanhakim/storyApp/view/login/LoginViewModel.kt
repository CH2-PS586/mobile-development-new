package com.dicoding.elanhakim.storyApp.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.elanhakim.storyApp.data.local.repository.Repository
import com.dicoding.elanhakim.storyApp.data.remote.response.user.User
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