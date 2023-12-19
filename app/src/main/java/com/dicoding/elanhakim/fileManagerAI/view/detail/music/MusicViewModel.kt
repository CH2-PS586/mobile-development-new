package com.dicoding.elanhakim.fileManagerAI.view.detail.music

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.elanhakim.fileManagerAI.data.local.repository.Repository
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User

class MusicViewModel  constructor(private val repository: Repository) : ViewModel() {

    fun getSessionData(): LiveData<User> =
        repository.getSession().asLiveData()

    fun getMusic(token: String) = repository.getMusic(token)

}