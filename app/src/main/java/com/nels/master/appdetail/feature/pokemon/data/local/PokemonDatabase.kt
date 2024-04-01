package com.nels.master.appdetail.feature.pokemon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PokemonEntity::class],
    version = 2,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao
}

