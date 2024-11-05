package com.example.rickandmortyapp.ui.screens.character

import android.annotation.SuppressLint
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortyapp.data.api.models.Character
import com.example.rickandmortyapp.data.viewmodel.CharacterViewModel
import com.example.rickandmortyapp.databinding.ActivityCharacterBinding
import com.example.rickandmortyapp.ui.screens.character.rv.RVEpisodesAdapter
import com.example.rickandmortyapp.utils.loadImage
import kotlinx.coroutines.launch

class CharacterActivity : AppCompatActivity() {

    private lateinit var rvEpisodesAdapter: RVEpisodesAdapter
    private lateinit var binding: ActivityCharacterBinding
    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRV()
        initUiStateLifecycle()
        getCharacterId()
    }

    private fun initRV() {
        rvEpisodesAdapter = RVEpisodesAdapter()
        binding.rvCharacterEpisodes.apply {
            layoutManager = LinearLayoutManager(this@CharacterActivity)
            adapter = rvEpisodesAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initUiStateLifecycle() {
        lifecycleScope.launch {
            characterViewModel.uiState.collect { uiState ->
                with(binding) {
                    uiState.character?.let { character ->
                        tvCharacterName.text = character.name
                        tvCharacterLocation.text = character.location.name
                        tvCharacterSpecie.text = character.species
                        ivCharacterPhoto.loadImage(character.image)
                    }
                    if (uiState.episodes.isNotEmpty()) {
                        rvEpisodesAdapter.episodes = uiState.episodes
                        rvEpisodesAdapter.notifyDataSetChanged()
                    }
                    pbCharacter.visibility = if (uiState.isCharacterLoading) View.VISIBLE else View.INVISIBLE
                    pbEpisodes.visibility = if (uiState.isEpisodeListLoading) View.VISIBLE else View.INVISIBLE
                }
            }
        }
    }

    private fun getCharacterId() {
        val character = if (SDK_INT == 33) {
            intent.extras?.getParcelable("characters", Character::class.java)
        } else {
            @Suppress("DEPRECATION") intent.extras?.get("character") as Character
        }
        val characterId = intent.extras?.getInt(CHARACTER_ID)
        characterId?.let {
            characterViewModel.getCharacterInfo(characterId)
        }
    }

    companion object {
        const val CHARACTER_ID = "characterId"
    }
}