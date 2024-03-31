package com.nels.master.appdetail.core





import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.nels.master.appdetail.components.CircularProfile

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            CircularProfile(
                textColor = Color.Black,
                backgroundColor = Color.Transparent,
                profileText = "Nelson Gustavo Ek Perera",
                url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/2.png"
            )

            Text(text = "Nelson Gustavo El Perera")
        }

    }
}
