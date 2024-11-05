package com.example.rickandmortyapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.api.models.Character
import com.example.rickandmortyapp.data.api.retrofit.RetrofitService
import com.example.rickandmortyapp.data.db.dao.CharactersDao
import com.example.rickandmortyapp.ui.screens.characters.uiState.CharactersUiState
import com.example.rickandmortyapp.utils.toEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersViewModel: ViewModel() {

    lateinit var charactersDao: CharactersDao

    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    private val retrofitApi by lazy {
        RetrofitService.getInstance()
    }

    fun updateSaveCharacterById(id: Int): Int? {
        _uiState.value.characters?.results?.let { characters ->
            characters.onEachIndexed { index, character ->
                if (character.id == id) {
                    saveCharacterIntoDB(character)
                    val updatedCharacter = character.copy(
                        isSaved = character.isSaved.not()
                    )
                    val newCharacterList = (characters as ArrayList)
                    newCharacterList[index] = updatedCharacter
                    return index
                }
            }
        }
        return null
    }

    private fun saveCharacterIntoDB(character: Character) {
        viewModelScope.launch {
            if (character.isSaved.not()) {
                charactersDao.insertCharacter(character.toEntity())
            } else {
                charactersDao.deleteCharacter(character.toEntity())
            }
        }
    }

    fun removeCharacterById(idToRemove: Int): Int? {
        _uiState.value.characters?.results?.let { characters ->
            characters.onEachIndexed { index, character ->
                if (character.id == idToRemove) {
                    val newCharacterList = (characters as ArrayList).apply {
                        remove(character)
                    }
                    _uiState.update {
                        _uiState.value.copy(
                            characters = _uiState.value.characters?.copy(
                                results = newCharacterList
                            )
                        )
                    }
                    return  index
                }
            }
        }
        return null
    }

    fun getCharacters() {
        viewModelScope.launch {
            val savedCharacters = charactersDao.getAllCharacters()
            if (savedCharacters.isNotEmpty()) {
                val characterResponse = retrofitApi.getCharacters()
                val characters = retrofitApi.getCharacters().results as ArrayList
                savedCharacters.forEach { dbCharacter ->
                    val savedCharacter = characters.find { it.id == dbCharacter.id }?.copy(
                        isSaved = true
                    )
                    savedCharacter?.let {
                        characters.forEachIndexed { index, character ->
                            if (savedCharacter.id == character.id) {
                                characters[index] = savedCharacter
                                return@forEach
                            }
                        }
                    }
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    characters = characterResponse.copy(
                        results = characters
                    )
                )
            } else {
                val characters = retrofitApi.getCharacters()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    characters = characters
                )
            }
        }
    }
}