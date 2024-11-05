package com.example.rickandmortyapp.data.api.retrofit

import com.example.rickandmortyapp.data.api.models.Character
import com.example.rickandmortyapp.data.api.models.Characters
import com.example.rickandmortyapp.data.api.models.CompleteLocation
import com.example.rickandmortyapp.data.api.models.Episode
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // https://rickandmortyapi.com/api/character
    @GET("character")
    suspend fun getCharacters(): Characters

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character

    @GET("episode/{episodeId}")
    suspend fun getEpisodeById(@Path("episodeId") id: Int): Episode

    @GET("location/{locationId}")
    suspend fun getLocationById(@Path("locationId") id: Int): CompleteLocation

}