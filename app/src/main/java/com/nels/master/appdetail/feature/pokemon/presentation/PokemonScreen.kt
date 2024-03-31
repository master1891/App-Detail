package com.nels.master.appdetail.feature.pokemon.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nels.master.appdetail.components.PokemonItem

@Composable
fun PokemonScreen(
    pokemonState: PokemonState,
    navController: NavHostController,
    onEvent: (PokemonUIEvent) -> Unit
) {

    if (pokemonState.listPokemons.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items(pokemonState.listPokemons.size) { index ->
                PokemonItem(
                    pokemon = pokemonState.listPokemons[index],
                    navHostController = navController
                )
                Spacer(modifier = Modifier.height(16.dp))

                //logica para cargar mas elementos
                if (index >= pokemonState.listPokemons.size - 1 && !pokemonState.loading) {
                    onEvent(PokemonUIEvent.Paginate)
                }

            }
        }
    }

}

