package com.dicoding.elanhakim.fileManagerAI.data.di

import android.content.Context
import com.dicoding.elanhakim.fileManagerAI.data.local.pref.UserPreference
import com.dicoding.elanhakim.fileManagerAI.data.local.pref.dataStore
import com.dicoding.elanhakim.fileManagerAI.data.remote.retrofit.ApiConfig
import com.dicoding.elanhakim.fileManagerAI.data.local.repository.Repository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.accessToken)
        return Repository.getInstance(apiService, pref)
    }

}