package com.dicoding.elanhakim.storyApp.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.elanhakim.storyApp.data.local.repository.Repository
import com.dicoding.elanhakim.storyApp.data.remote.response.user.User

class DetailViewModel  constructor(private val repository: Repository) : ViewModel() {

    fun getSessionData(): LiveData<User> =
        repository.getSession().asLiveData()
    fun getMusic(token: String) = repository.getMusic(token)

}