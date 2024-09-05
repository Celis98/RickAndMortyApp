package com.example.rickandmortyapp.data.api.models

data class Characters(
    val info: BaseResponse,
    val results: List<Character>
)
