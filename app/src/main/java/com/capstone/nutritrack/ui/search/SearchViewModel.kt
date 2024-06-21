package com.capstone.nutritrack.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.nutritrack.data.api.ApiConfig
import com.capstone.nutritrack.response.SearchFoodResponse
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<List<SearchFoodResponse>>()
    val searchResults: LiveData<List<SearchFoodResponse>> get() = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun searchFood(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().searchFood(query)
                if (response.isSuccessful) {
                    _searchResults.value = response.body() ?: emptyList()
                } else {
                    _searchResults.value = emptyList()
                }
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
