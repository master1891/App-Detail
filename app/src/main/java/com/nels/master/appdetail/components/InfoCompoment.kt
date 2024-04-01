package com.nels.master.appdetail.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nels.master.appdetail.R

@Composable
fun InfoComponent(modifier: Modifier, onTap: () -> Unit) {
    ElevatedButton(
        elevation =  ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 20.dp
        ),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color(0xFFE6DFF7),
            contentColor = Color.Black

        ),
        modifier = modifier
            .then(Modifier.size(60.dp))
            .clip(CircleShape),

        onClick = {
            onTap()
        }
    ) {

        Icon(
            painter = painterResource(R.drawable.baseline_info),
            contentDescription = null,
        )


    }
}