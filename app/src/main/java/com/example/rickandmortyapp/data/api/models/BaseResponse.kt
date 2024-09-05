package com.example.rickandmortyapp.data.api.models

data class BaseResponse(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)
