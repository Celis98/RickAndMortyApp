package com.example.rickandmortyapp.data.api.models

import com.google.gson.annotations.SerializedName

data class Character(
    val id: Int,
    val name: String,
    val location: Location,
    val species: String,
    val status: String,
    val gender: String,
    val image: String,
    @SerializedName("episode")
    val episodes: List<String>
)
