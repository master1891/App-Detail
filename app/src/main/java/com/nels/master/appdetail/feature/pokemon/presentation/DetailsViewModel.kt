package com.nels.master.appdetail.feature.pokemon.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nels.master.appdetail.feature.pokemon.domain.repository.PokemonRepository
import com.nels.master.appdetail.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Android Devs Academy (Ahmed Guedmioui)
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonId = savedStateHandle.get<Int>("pokemonId")

    private var _detialsState = MutableStateFlow(DetailsState())
    val detailsState = _detialsState.asStateFlow()

    init {
        getPokemon(pokemonId ?: -1)
    }

    private fun getPokemon(id: Int) {
        viewModelScope.launch {
            _detialsState.update {
                it.copy(isLoading = true)
            }

            pokemonRepository.getInfoPokemon(id).collectLatest { result ->
                when (result) {
                    is Response.Error -> {
                        _detialsState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Response.Processing -> {
                        _detialsState.update {
                            it.copy(isLoading = result.processing)
                        }
                    }

                    is Response.Success -> {
                        result.data?.let { pokemon ->
                            _detialsState.update {
                                it.copy(pokemon = pokemon)
                            }
                        }
                    }
                }
            }
        }
    }
}























