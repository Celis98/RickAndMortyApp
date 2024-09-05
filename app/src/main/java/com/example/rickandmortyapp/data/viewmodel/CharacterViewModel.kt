package com.example.rickandmortyapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.api.models.Character
import com.example.rickandmortyapp.data.api.models.Episode
import com.example.rickandmortyapp.data.api.retrofit.RetrofitService
import com.example.rickandmortyapp.ui.screens.character.uiState.CharacterUiState
import com.example.rickandmortyapp.utils.getIdFromUrl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(CharacterUiState())
    val uiState: StateFlow<CharacterUiState> = _uiState.asStateFlow()

    private val retrofitApi by lazy {
        RetrofitService.getInstance()
    }

    fun getCharacterInfo(id: Int) {
        viewModelScope.launch {
            val character = retrofitApi.getCharacterById(id)
            val newUiState = _uiState.value.copy(
                character = character,
                isCharacterLoading = false
            )
            _uiState.value = newUiState
            setCharacterEpisodes(character)
        }
    }

    private suspend fun setCharacterEpisodes(character: Character) {
        val episodes = arrayListOf<Episode>()
        character.episodes.
            subList(0, character.episodes.size/2)
                .forEach { url ->
            val episodeId = url.getIdFromUrl()
            episodeId?.let { id ->
                episodes.add(retrofitApi.getEpisodeById(id))
            }
        }
        _uiState.update {
            _uiState.value.copy(
                episodes = episodes,
                isEpisodeListLoading = false
            )
        }
    }
}