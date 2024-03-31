package com.nels.master.appdetail.feature.pokemon.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nels.master.appdetail.feature.pokemon.domain.repository.PokemonRepository
import com.nels.master.appdetail.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel() {

    private val _pokemonState : MutableStateFlow<PokemonState> = MutableStateFlow(PokemonState())
    val pokemonState get() = _pokemonState.asStateFlow()

    init {
        getPokemons(false)
    }


    fun uiEvent(uiEvent: PokemonUIEvent){
        when(uiEvent){
            PokemonUIEvent.Navigate -> {
                _pokemonState.update {
                    it.copy(mainScreen = !pokemonState.value.mainScreen)
                }
            }
            PokemonUIEvent.Paginate -> {
               getPokemons(false)
            }
        }
    }

    private fun getPokemons(fetchRemote: Boolean){
        viewModelScope.launch {
            _pokemonState.update { it.copy(loading = true) }
            pokemonRepository.getPokemons(_pokemonState.value.offsetPageIncrement,fetchRemote).collectLatest{ response ->
                when(response){
                    is Response.Error -> {
                        _pokemonState.update {
                            it.copy(loading = false)
                        }
                    }

                    is Response.Success -> {
                        response.data?.let { pokemonsNotNull ->
                            _pokemonState.update {
                                it.copy(
                                    listPokemons = pokemonsNotNull,
                                    offsetPageIncrement = (_pokemonState.value.offsetPageIncrement + _pokemonState.value.offsetPageIncrement)
                                )
                            }
                        }
                    }
                    is Response.Processing -> {
                        _pokemonState.update {
                            it.copy(loading = response.processing)
                        }
                    }
                }
            }

        }
    }




}