package com.nels.master.appdetail.util

sealed class Screen(val route: String) {
    object  Home : Screen("HomeScreem")
    object  Profile : Screen("ProfileScreen")
    object  Pokemons : Screen("Pokemons")
    object  DetailPokemons : Screen("DetailPokemons")

}