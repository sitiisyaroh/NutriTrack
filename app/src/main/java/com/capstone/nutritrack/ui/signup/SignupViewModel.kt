package com.capstone.nutritrack.ui.signup

import androidx.lifecycle.ViewModel
import com.capstone.nutritrack.data.repository.UserRepository

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {
    fun register(fullName: String, email: String, password: String) = repository.signup(fullName, email, password)
}
