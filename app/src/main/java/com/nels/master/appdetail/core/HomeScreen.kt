package com.nels.master.appdetail.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nels.master.appdetail.R
import com.nels.master.appdetail.feature.location.presentation.LoacationScreen
import com.nels.master.appdetail.feature.pokemon.presentation.PokemonScreen
import com.nels.master.appdetail.feature.pokemon.presentation.PokemonUIEvent
import com.nels.master.appdetail.feature.pokemon.presentation.PokemonViewModel
import com.nels.master.appdetail.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val pokemonViewModel = hiltViewModel<PokemonViewModel>()
    val pokemonState = pokemonViewModel.pokemonState.collectAsState().value

    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomNavController = bottomNavController,
                pokemonViewModel::uiEvent
            )
        },
        topBar = {

            TopAppBar(
                title = {
                    Text(
                        text = when(pokemonState.screen){

                            Screen.DetailPokemons -> stringResource(R.string.detalle)
                            Screen.Location -> stringResource(R.string.location)
                            Screen.Pokemons -> stringResource(R.string.pokemons)
                            Screen.Profile -> stringResource(R.string.profile)
                            Screen.Nothing -> TODO()
                            Screen.Home ->  TODO()
                        }
                    )
                },
                modifier = Modifier.shadow(2.dp),
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.Profile.route
            ) {
                composable(Screen.Profile.route) {
                    ProfileScreen()
                }

                composable(Screen.Location.route) {
                    LoacationScreen()
                }

                composable(Screen.Pokemons.route) {
                    PokemonScreen(
                        pokemonState = pokemonState,
                        navController = navController,
                        pokemonViewModel::uiEvent
                    )
                }
            }
        }

    }
}

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController, onEvent: (Screen,PokemonUIEvent) -> Unit
) {

    val items = listOf(
        BottomItem(
            title = "Profile",
            icon = Icons.Rounded.AccountCircle
        ),
        BottomItem(
            title = "Location",
            icon = Icons.Rounded.LocationOn
        ), BottomItem(
            title = "Pokemons",
            icon = Icons.Rounded.List
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(selected = selected.intValue == index, onClick = {
                    selected.intValue = index
                    when (selected.intValue) {
                        0 -> {
                            onEvent(Screen.Profile,PokemonUIEvent.Navigate)
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.Profile.route)
                        }

                        1 -> {
                            onEvent(Screen.Location,PokemonUIEvent.Navigate)
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.Location.route)
                        }

                        2 -> {
                            onEvent(Screen.Pokemons,PokemonUIEvent.Navigate)
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.Pokemons.route)
                        }
                    }
                }, icon = {
                    Icon(
                        imageVector = bottomItem.icon,
                        contentDescription = bottomItem.title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }, label = {
                    Text(
                        text = bottomItem.title, color = MaterialTheme.colorScheme.onBackground
                    )
                })
            }
        }
    }

}


data class BottomItem(
    val title: String, val icon: ImageVector
)