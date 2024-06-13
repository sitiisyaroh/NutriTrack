package com.capstone.nutritrack.ui.setgoals

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.nutritrack.data.ResultState
import com.capstone.nutritrack.data.repository.UserRepository
import com.capstone.nutritrack.data.request.SetGoalsRequest
import com.capstone.nutritrack.response.SetGoalsResponse

class SetGoalsViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun setGoals(
        gender: String,
        dateOfBirth: String,
        height: Int,
        weight: Int,
        goalWeight: Int
    ): LiveData<ResultState<SetGoalsResponse>> {
        val request = SetGoalsRequest(gender, dateOfBirth, height, weight, goalWeight)
        return userRepository.setGoals(request)
    }
}