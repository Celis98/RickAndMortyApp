package com.example.rickandmortyapp.ui.screens.character.uiState

import com.example.rickandmortyapp.data.api.models.Character
import com.example.rickandmortyapp.data.api.models.Episode

data class CharacterUiState(
    val character: Character? = null,
    val episodes: List<Episode> = emptyList(),
    val isCharacterLoading: Boolean = true,
    val isEpisodeListLoading: Boolean = true,
)
