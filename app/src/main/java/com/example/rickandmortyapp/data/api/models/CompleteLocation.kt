package com.example.rickandmortyapp.data.api.models

data class CompleteLocation(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String> = emptyList()
)
