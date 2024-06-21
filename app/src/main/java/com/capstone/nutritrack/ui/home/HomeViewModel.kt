package com.capstone.nutritrack.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.nutritrack.data.ResultState
import com.capstone.nutritrack.data.repository.UserRepository
import com.capstone.nutritrack.response.GetFoodIntakeResponseItem
import com.capstone.nutritrack.response.GetSetGoalsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(private val repository: UserRepository) : ViewModel() {

    private val _goals = MutableLiveData<List<GetSetGoalsResponse>>()
    val goals: LiveData<List<GetSetGoalsResponse>> get() = _goals

    private val _foodIntake = MutableLiveData<ResultState<List<GetFoodIntakeResponseItem>>>()
    val foodIntake: LiveData<ResultState<List<GetFoodIntakeResponseItem>>> get() = _foodIntake

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getSession() = repository.getSession().asLiveData(Dispatchers.IO)

    fun fetchGoals(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val goalsResponse = repository.getGoals(userId)
                _goals.postValue(goalsResponse)
            } catch (e: HttpException) {
                _error.postValue("Error fetching goals: ${e.message()}")
            } catch (e: Exception) {
                _error.postValue("Unexpected error: ${e.message}")
            }
        }
    }

    fun fetchFoodIntake(userId: String) {
        viewModelScope.launch {
            try {
                val foodIntakeResponse = repository.getFoodIntake(userId)
                _foodIntake.postValue(foodIntakeResponse)
            } catch (e: Exception) {
                _error.postValue("Error fetching food intake: ${e.message}")
            }
        }
    }
}