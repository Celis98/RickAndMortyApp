package com.example.rickandmortyapp.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.db.dao.CharactersDao
import kotlinx.coroutines.launch

class FavoritesViewModel: ViewModel() {

    lateinit var charactersDao: CharactersDao

    fun getAllFavoriteCharacters() {
        viewModelScope.launch {
            charactersDao.getAllCharacters().forEach {
                Log.d("TEST--", "${it.name}")
            }
        }
    }

}