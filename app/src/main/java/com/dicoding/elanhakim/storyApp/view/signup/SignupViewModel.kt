package com.dicoding.elanhakim.storyApp.view.signup

import androidx.lifecycle.ViewModel
import com.dicoding.elanhakim.storyApp.data.local.repository.Repository
import com.dicoding.elanhakim.storyApp.data.remote.retrofit.RegisterRequest

class SignupViewModel(
    private val repository: Repository
    ) : ViewModel() {

    fun signup(request: RegisterRequest) = repository.signUp(request)

}