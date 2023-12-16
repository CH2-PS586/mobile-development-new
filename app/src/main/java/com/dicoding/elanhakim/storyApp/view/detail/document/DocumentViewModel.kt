package com.dicoding.elanhakim.storyApp.view.detail.document

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.elanhakim.storyApp.data.local.repository.Repository
import com.dicoding.elanhakim.storyApp.data.remote.response.user.User
import kotlinx.coroutines.launch

class DocumentViewModel(
    private val repository: Repository
)
    : ViewModel(){

    fun getSessionData(): LiveData<User> =
        repository.getSession().asLiveData()

}