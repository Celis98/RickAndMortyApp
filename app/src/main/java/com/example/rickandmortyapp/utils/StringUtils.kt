package com.example.rickandmortyapp.utils

fun String.getIdFromUrl(): Int? =
    this.split("/").lastOrNull()?.toIntOrNull()
