package com.example.rickandmortyapp.utils

import com.example.rickandmortyapp.data.api.models.Character
import com.example.rickandmortyapp.data.db.entities.CharacterEntity

fun Character.toEntity() =
    CharacterEntity(
        id = this.id,
        name = this.name,
        image = this.image
    )