package com.nels.master.appdetail.feature.pokemon.presentation

sealed interface PokemonUIEvent {
    object Paginate: PokemonUIEvent
    object Navigate: PokemonUIEvent

}