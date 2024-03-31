package com.nels.master.appdetail.feature.pokemon.data.remote

import com.nels.master.appdetail.feature.pokemon.data.remote.dto.detail.DetailPokemonDto
import com.nels.master.appdetail.feature.pokemon.data.remote.dto.mainresult.PokemonDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApiService {

    @GET("pokemon/")
    suspend fun getPokemons(@Query("limit") limit: Int = 10 ,@Query("offset") offset: Int): PokemonDto

    @GET()
    suspend fun getDetailPokemon(@Url url: String): DetailPokemonDto

    companion object{
        const val URL_IMAGE = "https://pokeapi.co/api/v2/pokemon/27/"
        const val URL_BASE = "https://pokeapi.co/api/v2/"
    }
}