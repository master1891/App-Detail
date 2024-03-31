package com.nels.master.appdetail.feature.pokemon.data.remote.dto.mainresult

import com.nels.master.appdetail.feature.pokemon.data.remote.dto.detail.DetailPokemonDto

data class PokemonDto(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<DataExtraPokemon>,
    //data adicional correspondiente al segundo endpoint
    var detailPokemonDto: DetailPokemonDto
)