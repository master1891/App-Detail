package com.nels.master.appdetail.feature.pokemon.presentation

import com.nels.master.appdetail.feature.pokemon.domain.models.Pokemon
import com.nels.master.appdetail.util.Screen

data class PokemonState(
    val listPokemons: List<Pokemon> = emptyList(),
    val loading : Boolean = false,
    val screen : Screen = Screen.Profile,
    var offsetPageIncrement : Int =  0,
)


