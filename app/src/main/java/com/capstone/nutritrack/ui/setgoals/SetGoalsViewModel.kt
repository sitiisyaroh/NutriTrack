package com.capstone.nutritrack.ui.setgoals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutritrack.data.ResultState
import com.capstone.nutritrack.data.repository.UserRepository
import com.capstone.nutritrack.data.request.SetGoalsRequest
import com.capstone.nutritrack.response.SetGoalsResponse
import kotlinx.coroutines.launch

class SetGoalsViewModel(private val repository: UserRepository) : ViewModel() {

    private val _setGoalsResult = MutableLiveData<ResultState<SetGoalsResponse>>()
    val setGoalsResult: LiveData<ResultState<SetGoalsResponse>> = _setGoalsResult

    fun setGoals(gender: String, dateOfBirth: String, height: Int, weight: Int, goalWeight: Int): LiveData<ResultState<SetGoalsResponse>> {
        _setGoalsResult.value = ResultState.Loading

        viewModelScope.launch {
            try {
                val response = repository.setGoals(gender, dateOfBirth, height, weight, goalWeight)
                _setGoalsResult.value = ResultState.Success(response)
            } catch (e: Exception) {
                _setGoalsResult.value = ResultState.Error(e.message ?: "An error occurred")
            }
        }
        return setGoalsResult
    }
}