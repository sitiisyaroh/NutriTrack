package com.capstone.nutritrack.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.capstone.nutritrack.data.pref.UserModel
import com.capstone.nutritrack.data.repository.UserRepository
import com.capstone.nutritrack.response.GetSetGoalsResponse
import kotlinx.coroutines.launch

class AccountViewModel (private val repository: UserRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }


    fun getGoals(userId: String): LiveData<List<GetSetGoalsResponse>> = liveData {
        val goalsResponse = repository.getGoals(userId)
        emit(goalsResponse)
    }

}