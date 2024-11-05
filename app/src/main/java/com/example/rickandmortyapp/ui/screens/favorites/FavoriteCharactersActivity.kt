package com.example.rickandmortyapp.ui.screens.favorites

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.data.db.DatabaseClient
import com.example.rickandmortyapp.data.viewmodel.FavoritesViewModel

class FavoriteCharactersActivity : AppCompatActivity() {

    private val favoritesViewModel: FavoritesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_characters)
        initDB()
        favoritesViewModel.getAllFavoriteCharacters()
    }

    private fun initDB() {
        favoritesViewModel.charactersDao = DatabaseClient.getDBClient(this).charactersDao()
    }
}