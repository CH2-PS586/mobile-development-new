package com.dicoding.elanhakim.fileManagerAI.view.detail.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.elanhakim.fileManagerAI.data.local.repository.Repository
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User

class PictureViewModel  constructor(private val repository: Repository) : ViewModel() {

    fun getSessionData(): LiveData<User> =
        repository.getSession().asLiveData()

    fun getCollage(token: String) = repository.getCollage(token)

    fun getFood(token: String) = repository.getFood(token)

    fun getFriends(token: String) = repository.getFriends(token)

    fun getMemes(token: String) = repository.getMemes(token)

    fun getPets(token: String) = repository.getPets(token)

    fun getSelfie(token: String) = repository.getSelfie(token)

}