package com.nels.master.appdetail.feature.pokemon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class PokemonEntity(
    @PrimaryKey
    val id: Int?,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprite: String,
    val isFavorite:Boolean
)