package com.nels.master.appdetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nels.master.appdetail.R


@Composable
fun FavoriteComponent(
    iSFavorite: Boolean = false, onTap: (isFavorite: Boolean) -> Unit
) {
    var thumbIconLiked by remember {
        mutableStateOf(iSFavorite)
    }

    var painter by remember {
        mutableStateOf(iSFavorite)
    }


    IconButton(
        onClick = {
            thumbIconLiked = !thumbIconLiked
            onTap(thumbIconLiked)
        }
    ) {
        Icon(
            painter = painterResource(
            id = if (thumbIconLiked) {
                R.drawable.favorite
            } else {
                R.drawable.favorite_border
            }), contentDescription = null,
            tint = if (thumbIconLiked) {
                Color.Red
            } else {
                Color.LightGray
            }
        )

    }

}