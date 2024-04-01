package com.nels.master.appdetail

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.nels.master.appdetail.components.RationaleAlert
import com.nels.master.appdetail.core.HomeScreen
import com.nels.master.appdetail.feature.pokemon.presentation.DetailsScreen
import com.nels.master.appdetail.ui.theme.AppDetailTheme
import com.nels.master.appdetail.util.Screen
import com.nels.master.appdetail.util.hasLocationPermission
import com.nels.master.pruebaopenpay.features.locationfeature.viewmodels.MainViewMoldel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //val viewModel = hiltViewModel<PokemonViewModel>()
            val navController = rememberNavController()

            Permisions(context = this, mainViewModel = hiltViewModel<MainViewMoldel>() )

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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permisions(context : Context, mainViewModel: MainViewMoldel){

    val permisionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )

    LaunchedEffect(!context.hasLocationPermission()) {
        permisionsState.launchMultiplePermissionRequest()
    }
    when{
        permisionsState.allPermissionsGranted ->{
            mainViewModel.requestLocation(MainViewMoldel.PermissionEvent.Granted)
        }
        permisionsState.shouldShowRationale ->{
            RationaleAlert(onDismiss = { /*TODO*/ }) {
                permisionsState.launchMultiplePermissionRequest()
            }
        }

        !permisionsState.allPermissionsGranted && !permisionsState.shouldShowRationale -> {
            LaunchedEffect(Unit) {
                mainViewModel.requestLocation(MainViewMoldel.PermissionEvent.Revoked)
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