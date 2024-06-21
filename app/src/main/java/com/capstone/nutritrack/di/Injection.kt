package com.capstone.nutritrack.di

import android.content.Context
import com.capstone.nutritrack.data.api.ApiConfig
import com.capstone.nutritrack.data.pref.UserPreference
import com.capstone.nutritrack.data.pref.dataStore
import com.capstone.nutritrack.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService,pref)
    }
}