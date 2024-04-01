package com.nels.master.appdetail.feature.location.presentation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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

    val cameraState = rememberCameraPositionState()
    GoogleMap(
        cameraPositionState = cameraState,
        modifier = Modifier.fillMaxSize(),
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