package com.example.rickandmortyapp.ui.screens.characters.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.data.api.models.Character
import com.example.rickandmortyapp.databinding.CharacterViewBinding

class RVCharactersAdapter(
    private val onEpisodesClickListener: (id: Int) -> Unit,
    private val onLocationClickListener: (id: Int) -> Unit,
    private val onDeleteClickListener: (id: Int) -> Unit,
    private val onSaveClickListener: (id: Int) -> Unit,
): RecyclerView.Adapter<CharacterPostViewHolder>() {

    var characters = emptyList<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterPostViewHolder {
        val binding = CharacterViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterPostViewHolder(
            binding = binding,
            onEpisodesClickListener = onEpisodesClickListener,
            onLocationClickListener = onLocationClickListener,
            onDeleteClickListener = onDeleteClickListener,
            onSaveClickListener = onSaveClickListener
        )
    }

    override fun onBindViewHolder(holder: CharacterPostViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int = characters.size

}