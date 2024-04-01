package com.nels.master.appdetail.feature.location.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.nels.master.appdetail.R
import com.nels.master.appdetail.components.BottomSheet
import com.nels.master.appdetail.components.InfoComponent
import com.nels.master.appdetail.feature.location.domain.models.Posicion
import com.nels.master.pruebaopenpay.features.locationfeature.viewmodels.MainViewMoldel


@Composable
fun LoacationScreen() {
    val mainViewMoldel = hiltViewModel<MainViewMoldel>()
    mainViewMoldel.getPositions()
    RenderMap(mainViewMoldel)
}

@Composable
fun RenderMap(mainViewMoldel: MainViewMoldel) {

    val state = mainViewMoldel.statePosition
    var showSheet by rememberSaveable() { mutableStateOf(false) }

    val cameraState = rememberCameraPositionState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            cameraPositionState = cameraState,
            modifier = Modifier.matchParentSize(),
            properties = MapProperties(
                isMyLocationEnabled = true,
                isBuildingEnabled = true,
                mapType = MapType.NORMAL,
            )

        ) {

            if (mainViewMoldel.statePosition.status == MainViewMoldel.PositionEvent.Success) {

                Log.d("LoacationScreen: ", "State success")
                for (position in state.positions) {
                    Log.d("LoacationScreen: ", "Creando marcador ...")
                    createMarker(position)
                }
                Log.d("LoacationScreen: ", "Terminó de crear markers")
                if (state.positions.isNotEmpty()) {
                    Log.d("LoacationScreen: ", "Usando el primer punto para centar")
                    val location = state.positions[0]

                    LaunchedEffect(true) {
                        cameraState.centerOnLocation(LatLng(location.latitud, location.latitud))
                    }
                }
            }

        }

        InfoComponent(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            showSheet = true
        }

        if (showSheet) {
            BottomSheet(onDismiss = { showSheet = false }) {
                ListLocations()
            }
        }
    }

}


@Composable
fun ListLocations() {
    val mainViewModel = hiltViewModel<MainViewMoldel>()
    val mainViewModelState = mainViewModel.statePosition
    if (mainViewModelState.positions.isNotEmpty()) {
        LazyColumn {

            items(items = mainViewModelState.positions) { position ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_location_pin_24),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = position.format(),
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }
            }
        }
    }
}

fun Posicion.format(): String {
    return "(${this.latitud} , ${this.latitud} )  ${this.fecha}"
}

@Composable
private fun createMarker(position: Posicion) {
    val marker = LatLng(position.latitud, position.longitud)

    Marker(
        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
        state = MarkerState(position = marker),
        title = position.fecha,
        snippet = "Punto establecido el ${position.fecha}",
        draggable = false
    )
}

private suspend fun CameraPositionState.centerOnLocation(
    location: LatLng
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        location,
        15f
    ),
    durationMs = 1500
)