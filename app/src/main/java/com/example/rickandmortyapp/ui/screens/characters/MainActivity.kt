package com.example.rickandmortyapp.ui.screens.characters

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyapp.data.db.DatabaseClient
import com.example.rickandmortyapp.data.viewmodel.CharactersViewModel
import com.example.rickandmortyapp.databinding.ActivityCharactersBinding
import com.example.rickandmortyapp.ui.screens.favorites.FavoriteCharactersActivity
import com.example.rickandmortyapp.ui.screens.character.CharacterActivity
import com.example.rickandmortyapp.ui.screens.character.CharacterActivity.Companion.CHARACTER_ID
import com.example.rickandmortyapp.ui.screens.characters.rv.RVCharactersAdapter
import com.example.rickandmortyapp.ui.screens.location.LocationActivity
import com.example.rickandmortyapp.ui.screens.location.LocationActivity.Companion.LOCATION_ID
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val charactersViewModel: CharactersViewModel by viewModels()
    private lateinit var binding: ActivityCharactersBinding
    private lateinit var rvCharactersAdapter: RVCharactersAdapter
    private lateinit var appDatabase: DatabaseClient.AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDB()
        charactersViewModel.getCharacters()
        initRV()
        initUiStateLifecycle()
        binding.btnFavoriteCharacters.setOnClickListener {
            startActivity(
                Intent(this, FavoriteCharactersActivity::class.java)
            )
        }
    }

    private fun initDB() {
        appDatabase = DatabaseClient.getDBClient(this)
        charactersViewModel.charactersDao = appDatabase.charactersDao()
    }

    private fun initRV() {
        rvCharactersAdapter = RVCharactersAdapter(
            onEpisodesClickListener = { characterId ->
                launchCharacterActivity(characterId)
            },
            onLocationClickListener = { locationId ->
                launchLocationActivity(locationId)
            },
            onDeleteClickListener = { id ->
                charactersViewModel.removeCharacterById(id)?.let { indexToRemove ->
                    rvCharactersAdapter.notifyItemRemoved(indexToRemove)
                }
            },
            onSaveClickListener = { id ->
                charactersViewModel.updateSaveCharacterById(id)?.let { indexToUpdate ->
                   rvCharactersAdapter.notifyItemChanged(indexToUpdate)
                }
            }
        )
        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = rvCharactersAdapter
        }
    }

    private fun launchLocationActivity(locationId: Int) {
        startActivity(
            Intent(
                this,
                LocationActivity::class.java
            ).apply {
                putExtras(
                    bundleOf(
                        LOCATION_ID to locationId
                    )
                )
            }
        )
    }

    private fun launchCharacterActivity(characterId: Int) {
//        val intent = Intent(this, CharacterActivity::class.java)
//        val bundle = bundleOf(
//            "characterId" to characterId
//        )
//        intent.putExtras(bundle)
//        startActivity(intent)

        startActivity(
            Intent(
                this,
                CharacterActivity::class.java
            ).apply {
                putExtras(
                    bundleOf(
                        CHARACTER_ID to characterId
                    )
                )
            }
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initUiStateLifecycle() {
        lifecycleScope.launch {
            charactersViewModel.uiState.collect { uiState ->
                uiState.characters?.results?.let { charactersList ->
                    rvCharactersAdapter.characters = charactersList
                    rvCharactersAdapter.notifyDataSetChanged()
                }
                binding.rvCharacters.visibility = if (uiState.isLoading) View.INVISIBLE else View.VISIBLE
                binding.pbCharacters.visibility = if (uiState.isLoading.not()) View.INVISIBLE else View.VISIBLE
            }
        }
    }
}