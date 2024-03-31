package com.nels.master.appdetail.feature.pokemon.data.converters


import com.nels.master.appdetail.feature.pokemon.data.local.PokemonEntity
import com.nels.master.appdetail.feature.pokemon.data.remote.dto.detail.DetailPokemonDto

import com.nels.master.appdetail.feature.pokemon.domain.models.Pokemon


//se usa cuando el adicciona;l ya esta seteado


fun DetailPokemonDto.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        height = height,
        weight = weight,
        name = name,
        sprite = sprites.back_default,
    )
}

fun DetailPokemonDto.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        height = height,
        weight = weight,
        name = name,
        sprite= sprites.back_default,
    )
}

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        id = id,
        height = height,
        weight = weight,
        name = name,
        sprite = sprite,
    )
}

fun Pokemon.toPokemonEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        height = height,
        weight = weight,
        name = name,
        sprite = sprite,
    )
}

