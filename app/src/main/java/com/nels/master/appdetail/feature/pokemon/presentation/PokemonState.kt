package com.nels.master.appdetail.feature.pokemon.presentation

import com.nels.master.appdetail.feature.pokemon.domain.models.Pokemon

data class PokemonState(
    val listPokemons: List<Pokemon> = emptyList(),
    val loading : Boolean = false,
    val mainScreen : Boolean = true,
     var offsetPageIncrement : Int =  25,
)

