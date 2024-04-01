package com.nels.master.appdetail.feature.location.domain.usecases

import com.google.android.gms.maps.model.LatLng
import com.nels.master.appdetail.feature.location.domain.service.IlocationService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationUsecase @Inject constructor(
    private val locationService: IlocationService
) {
    operator fun invoke():Flow<LatLng?>{
        return locationService.requestLocationUpdates()
    }
}