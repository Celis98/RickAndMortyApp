package com.example.rickandmortyapp.ui.screens.location.uiState

import com.example.rickandmortyapp.data.api.models.CompleteLocation

data class LocationUiState(
    val location: CompleteLocation? = null,
    val isLoading: Boolean = true
)
