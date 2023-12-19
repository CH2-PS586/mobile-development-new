package com.dicoding.elanhakim.fileManagerAI.view.signup

import androidx.lifecycle.ViewModel
import com.dicoding.elanhakim.fileManagerAI.data.local.repository.Repository
import com.dicoding.elanhakim.fileManagerAI.data.remote.retrofit.RegisterRequest

class SignupViewModel(
    private val repository: Repository
    ) : ViewModel() {

    fun signup(request: RegisterRequest) = repository.signUp(request)

}