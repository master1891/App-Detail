package com.nels.master.appdetail.feature.pokemon.domain.repository

import com.nels.master.appdetail.util.Response
import com.nels.master.appdetail.feature.pokemon.domain.models.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemons(offset:Int,fetchRemote: Boolean) : Flow<Response<List<Pokemon>>>
    suspend fun getInfoPokemon(idPokemon:Int) : Flow<Response<Pokemon>>

    suspend fun saveFavorite(pokemon: Pokemon) : Flow<Response<List<Pokemon>>>

}