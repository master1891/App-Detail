package com.nels.master.appdetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.nels.master.appdetail.feature.pokemon.domain.models.Pokemon
import com.nels.master.appdetail.feature.pokemon.presentation.PokemonViewModel
import com.nels.master.appdetail.util.Screen

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    navHostController: NavHostController
) {

    val pokemonViewModel = hiltViewModel<PokemonViewModel>()
    val pokemonState = pokemonViewModel.pokemonState.collectAsState()

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(pokemon.sprite)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val defaultColor = MaterialTheme.colorScheme.secondaryContainer

    Column(
        modifier = Modifier
            .width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        defaultColor
                    )
                )
            )
            .clickable {
                navHostController.navigate(Screen.DetailPokemons.route + "/${pokemon.id}")
            }
    ) {
        if (imageState is AsyncImagePainter.State.Error || imageState is AsyncImagePainter.State.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)

                ,
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = pokemon.name
                )
            }
        }

        if (imageState is AsyncImagePainter.State.Success) {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(22.dp)),
                painter = imageState.painter,
                contentDescription = pokemon.name,
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
            ,
            text = pokemon.name.capitalize(Locale.current),
            color = Color.Black,
            fontSize = 20.sp,
            maxLines = 1,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(6.dp))


        FavoriteComponent(pokemon.isFavorite) {
            val pokemon = pokemon.copy(isFavorite = it)
            pokemonViewModel.updateFavoritePokemon(pokemon)
        }

    }
}