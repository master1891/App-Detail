package com.nels.master.appdetail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun CircularProfile(
    modifier: Modifier = Modifier,
    textColor: Color = Color.DarkGray,
    backgroundColor: Color = Color.LightGray,
    profileText: String?,
    url: String,
    placeHolder: ImageVector = Icons.Rounded.ImageNotSupported
) {

    val imageProfile = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .crossfade(1000)
            .size(Size.ORIGINAL)
            .build()
    ).state

    when (imageProfile) {

        is AsyncImagePainter.State.Empty, is AsyncImagePainter.State.Error -> {

            profileText?.let { it ->
                it.formatProfile()?.let { finalString ->

                    Box(
                        modifier = modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = modifier
                                .clip(CircleShape)
                                .size(150.dp)
                                .background(backgroundColor),
                            contentAlignment = Alignment.Center

                        ) {
                            Text(
                                modifier = Modifier,
                                text = finalString,
                                fontSize = 35.sp,
                                color = textColor,
                                fontWeight = FontWeight.Medium
                            )
                        }

                    }


                } ?: run {
                    Icon(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .background(backgroundColor),
                        imageVector = placeHolder,
                        contentDescription = null
                    )
                }

            } ?: run {
                Icon(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(backgroundColor),
                    imageVector = placeHolder,
                    contentDescription = null
                )
            }

        }

        is AsyncImagePainter.State.Loading -> {

            Icon(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(backgroundColor),
                imageVector = placeHolder,
                contentDescription = null
            )

        }

        is AsyncImagePainter.State.Success -> {
            Image(
                modifier = Modifier
                    .height(150.dp)
                    .border(width = 1.dp, Color.LightGray, CircleShape),
                painter = imageProfile.painter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }


}

//Pequeña utilidad para formatear el nombre de perfil
fun String.formatProfile(): String? {

    val names = this.split("\\s+".toRegex())

    val res = if (names.isEmpty() || names[0].isEmpty()) {
        null
    } else {
        if (names[0].isValidName()) {
            if (names.size > 1)
                (names[0].elementAt(0).toString().plus(" ")
                    .plus(names[1].elementAt(0).toString())).toUpperCase(Locale.current)
            else {
                names[0].elementAt(0).toString().toUpperCase(Locale.current)
            }
        } else {
            null
        }
    }

    return res

}

fun String.isValidName(): Boolean {
    val isValidName = !this.elementAt(0).isDigit() && this.elementAt(0).isLetter()
    return isValidName
}

