package com.capstone.nutritrack.ui.signup

import androidx.lifecycle.ViewModel
import com.capstone.nutritrack.data.repository.UserRepository

class SignUpViewModel (private val respository: UserRepository) : ViewModel(){
    fun register(name: String, email: String, password: String) = respository.signup(name, email, password)
}