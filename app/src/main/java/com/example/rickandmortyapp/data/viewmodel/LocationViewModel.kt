package com.example.rickandmortyapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.api.retrofit.RetrofitService
import com.example.rickandmortyapp.ui.screens.location.uiState.LocationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(LocationUiState())
    val uiState: StateFlow<LocationUiState> = _uiState.asStateFlow()

    private val retrofitApi by lazy {
        RetrofitService.getInstance()
    }

    fun getLocationById(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                location = retrofitApi.getLocationById(id),
                isLoading = false
            )
        }
    }

}