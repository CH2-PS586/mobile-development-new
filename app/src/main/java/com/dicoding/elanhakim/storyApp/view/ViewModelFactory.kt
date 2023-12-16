package com.dicoding.elanhakim.storyApp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.elanhakim.storyApp.view.welcome.WelcomeViewModel
import com.dicoding.elanhakim.storyApp.view.login.LoginViewModel
import com.dicoding.elanhakim.storyApp.view.signup.SignupViewModel
import com.dicoding.elanhakim.storyApp.view.home.HomeViewModel
import com.dicoding.elanhakim.storyApp.data.local.repository.Repository
import com.dicoding.elanhakim.storyApp.data.di.Injection

import com.dicoding.elanhakim.storyApp.view.detail.document.DocumentViewModel
import com.dicoding.elanhakim.storyApp.view.detail.music.MusicViewModel

class ViewModelFactory private constructor(
    private val repository: Repository

    ) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(repository) as T

        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T

        } else if (modelClass.isAssignableFrom(SignupViewModel::class.java)) {
            return SignupViewModel(repository) as T

        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T

        }  else if (modelClass.isAssignableFrom(MusicViewModel::class.java)) {
            return MusicViewModel(repository) as T

        } else if (modelClass.isAssignableFrom(DocumentViewModel::class.java)) {
            return DocumentViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}


