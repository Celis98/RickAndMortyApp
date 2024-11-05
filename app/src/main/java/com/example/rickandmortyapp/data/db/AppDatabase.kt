package com.example.rickandmortyapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rickandmortyapp.data.db.dao.CharactersDao
import com.example.rickandmortyapp.data.db.entities.CharacterEntity

object DatabaseClient {

    private val dbClient: AppDatabase? = null

    fun getDBClient(context: Context): AppDatabase =
        dbClient ?: Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rick-and-morty-db"
        )
            .build()

    @Database(entities = [CharacterEntity::class], version = 1)
    abstract class AppDatabase: RoomDatabase() {
        abstract fun charactersDao(): CharactersDao
    }

}