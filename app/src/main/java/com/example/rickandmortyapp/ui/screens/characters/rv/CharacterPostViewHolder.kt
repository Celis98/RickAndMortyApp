package com.example.rickandmortyapp.ui.screens.characters.rv

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.data.api.models.Character
import com.example.rickandmortyapp.databinding.CharacterViewBinding
import com.example.rickandmortyapp.utils.getIdFromUrl
import com.example.rickandmortyapp.utils.loadCircleImage
import com.example.rickandmortyapp.utils.showToast

class CharacterPostViewHolder(
    private val binding: CharacterViewBinding,
    private val onEpisodesClickListener: (position: Int) -> Unit,
    private val onLocationClickListener: (id: Int) -> Unit,
    private val onDeleteClickListener: (index: Int) -> Unit,
    private val onSaveClickListener: (id: Int) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    fun bind(character: Character) {
        with(binding) {
            btnEpisodes.setOnClickListener {
                onEpisodesClickListener(character.id)
            }

            btnDelete.setOnClickListener {
                onDeleteClickListener(character.id)
            }

            btnLocation.setOnClickListener {
//                val locationId = character.location.url.getIdFromUrl()
//                if (locationId != null) {
//                    onLocationClickListener(locationId)
//                } else {
//                    btnLocation.context.showToast("No hay ubicacion")
//                }

                character.location.url.getIdFromUrl()?.let { id ->
                    onLocationClickListener(id)
                } ?: btnLocation.context.showToast("No hay ubicacion")
            }

            tvCharacterName.text = tvCharacterName.context.getString(
                R.string.character_name_with_position,
                character.id,
                character.name
            )

            tvCharacterGender.text =
                tvCharacterGender.context.getString(R.string.character_gender, character.gender)

            tvCharacterLocation.text =
                tvCharacterLocation.context.getString(R.string.character_location, character.location.name)

            ibFavorite.apply {
                setOnClickListener {
                    onSaveClickListener(character.id)
                }
                setImageDrawable(
                    ContextCompat.getDrawable(
                        ibFavorite.context,
                        if (character.isSaved) {
                            android.R.drawable.btn_star_big_on
                        } else {
                            android.R.drawable.btn_star_big_off
                        }
                    )
                )
            }

            ivCharacterPicture.loadCircleImage(character.image)

            ivCharacterStatus.apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                    ivCharacterStatus.context,
                    when(character.status) {
                        "Alive" -> R.drawable.ic_alive
                        "Dead" -> R.drawable.ic_dead
                        else -> R.drawable.ic_unknown
                    }
                ))
                setColorFilter(
                    ContextCompat.getColor(
                    ivCharacterStatus.context,
                    when(character.status) {
                        "Alive" -> R.color.green
                        "Dead" -> R.color.red
                        else -> R.color.blue
                    }
                ))
                setOnClickListener {
                    with(context) {
                        showToast(
                            getString(
                                when(character.status) {
                                    "Alive" -> R.string.status_alive
                                    "Dead" -> R.string.status_dead
                                    else -> R.string.status_unknown
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}