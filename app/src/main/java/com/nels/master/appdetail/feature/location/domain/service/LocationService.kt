package com.nels.master.appdetail.feature.location.domain.service

import android.content.Context
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.nels.master.appdetail.util.hasLocationPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class LocationService @Inject constructor(
    private val context:Context,
    private val locationProviderClient: FusedLocationProviderClient

): IlocationService {

    override fun requestLocationUpdates(): Flow<LatLng?> = callbackFlow {

        if (!context.hasLocationPermission()){
            trySend(null)
            return@callbackFlow
        }

        val request = LocationRequest.Builder(1000L)
            .setIntervalMillis(30000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
               locationResult.locations.lastOrNull()?.let {
                   trySend(LatLng(it.latitude,it.longitude))
               }
            }
        }
        locationProviderClient.requestLocationUpdates(request,locationCallback, Looper.getMainLooper())

        awaitClose {
            locationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    override fun requestCurrentLocation(): Flow<LatLng?> {
        TODO("Not yet implemented")
    }

}


interface IlocationService{
    fun requestLocationUpdates():Flow<LatLng?>
    fun requestCurrentLocation():Flow<LatLng?>
}
