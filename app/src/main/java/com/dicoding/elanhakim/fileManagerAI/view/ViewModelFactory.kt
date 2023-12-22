package com.dicoding.elanhakim.fileManagerAI.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.elanhakim.fileManagerAI.view.welcome.WelcomeViewModel
import com.dicoding.elanhakim.fileManagerAI.view.login.LoginViewModel
import com.dicoding.elanhakim.fileManagerAI.view.signup.SignupViewModel
import com.dicoding.elanhakim.fileManagerAI.view.home.HomeViewModel
import com.dicoding.elanhakim.fileManagerAI.data.local.repository.Repository
import com.dicoding.elanhakim.fileManagerAI.data.di.Injection
import com.dicoding.elanhakim.fileManagerAI.view.detail.document.DocumentViewModel
import com.dicoding.elanhakim.fileManagerAI.view.detail.music.MusicViewModel
import com.dicoding.elanhakim.fileManagerAI.view.detail.others.OthersViewModel
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureViewModel
import com.dicoding.elanhakim.fileManagerAI.view.detail.video.VideoViewModel
import com.dicoding.elanhakim.fileManagerAI.view.download.DownloadViewModel
import com.dicoding.elanhakim.fileManagerAI.view.upload.UploadViewModel

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

        } else if (modelClass.isAssignableFrom(OthersViewModel::class.java)) {
            return OthersViewModel(repository) as T

        } else if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
            return VideoViewModel(repository) as T

        } else if (modelClass.isAssignableFrom(PictureViewModel::class.java)) {
            return PictureViewModel(repository) as T

        } else if (modelClass.isAssignableFrom(UploadViewModel::class.java)) {
            return UploadViewModel(repository) as T

        } else if (modelClass.isAssignableFrom(DownloadViewModel::class.java)) {
            return DownloadViewModel(repository) as T

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


