package com.dicoding.elanhakim.fileManagerAI.view.detail.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.elanhakim.fileManagerAI.data.local.repository.Repository
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User

class VideoViewModel  constructor(private val repository: Repository) : ViewModel() {

    fun getSessionData(): LiveData<User> =
        repository.getSession().asLiveData()

    fun getVideo(token: String) = repository.getVideo(token)

}