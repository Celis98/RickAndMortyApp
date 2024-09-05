package com.example.rickandmortyapp.ui.screens.characters.uiState

import com.example.rickandmortyapp.data.api.models.Characters

data class CharactersUiState(
    val characters: Characters? = null,
    val isLoading: Boolean = true
)
