package com.capstone.nutritrack

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.nutritrack.data.repository.UserRepository
import com.capstone.nutritrack.di.Injection
import com.capstone.nutritrack.ui.account.AccountViewModel
import com.capstone.nutritrack.ui.home.HomeViewModel
import com.capstone.nutritrack.ui.login.LoginViewModel
import com.capstone.nutritrack.ui.main.MainViewModel
import com.capstone.nutritrack.ui.setgoals.SetGoalsViewModel
import com.capstone.nutritrack.ui.signup.SignUpViewModel
import com.capstone.nutritrack.ui.tracking.TrackingViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SetGoalsViewModel::class.java) -> {
                SetGoalsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(TrackingViewModel::class.java) -> {
                TrackingViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun clearInstance(){
            UserRepository.clearInstance()
            INSTANCE = null
        }

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context)).also { INSTANCE = it }
            }
        }
    }


}