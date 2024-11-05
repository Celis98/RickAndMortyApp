package com.example.rickandmortyapp.data.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val location: Location,
    val species: String,
    val status: String,
    val gender: String,
    val image: String,
    @SerializedName("episode")
    val episodes: List<String>,
    val isSaved: Boolean = false,
): Parcelable
