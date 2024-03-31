package com.nels.master.appdetail.feature.pokemon.presentation

import com.nels.master.appdetail.feature.pokemon.domain.models.Pokemon

/**
 * @author Android Devs Academy (Ahmed Guedmioui)
 */
data class DetailsState(
    val isLoading: Boolean = false,
    val pokemon: Pokemon? = null
)
