package com.nels.master.appdetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nels.master.appdetail.core.HomeScreen
import com.nels.master.appdetail.feature.pokemon.presentation.DetailsScreen
import com.nels.master.appdetail.ui.theme.AppDetailTheme
import com.nels.master.appdetail.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //val viewModel = hiltViewModel<PokemonViewModel>()
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screen.Home.route ){
                
                composable( Screen.Home.route){

                   HomeScreen(navController)
                }

                composable(
                    Screen.DetailPokemons.route + "/{pokemonId}",
                    arguments = listOf(
                        navArgument("pokemonId"){type = NavType.IntType}
                    )
                ){
                    DetailsScreen()
                }
            }


        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppDetailTheme {
        Greeting("Android")
    }
}