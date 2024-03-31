package com.nels.master.appdetail.feature.pokemon.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface PokemonDao{

    @Upsert
    fun upsertPokemon(pokemonEntity: List<PokemonEntity>)

    @Query("SELECT * FROM PokemonEntity")
    fun getPokemons() : List<PokemonEntity>

    @Query(" SELECT * FROM PokemonEntity WHERE id = :id")
     fun getPokemon(id: Int):PokemonEntity

}