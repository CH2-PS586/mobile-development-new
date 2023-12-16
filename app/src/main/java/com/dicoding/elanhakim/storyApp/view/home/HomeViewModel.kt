package com.dicoding.elanhakim.storyApp.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.elanhakim.storyApp.data.local.repository.Repository
import com.dicoding.elanhakim.storyApp.data.remote.response.user.User
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
)
    : ViewModel(){

    fun getSessionData(): LiveData<User> =
        repository.getSession().asLiveData()

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}