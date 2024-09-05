package com.example.rickandmortyapp.data.api.models

import com.google.gson.annotations.SerializedName

data class Episode(
    val id: Int,
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val season: String
)
