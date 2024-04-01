package com.nels.master.appdetail.feature.location.domain.usecases

import com.nels.master.appdetail.feature.location.domain.LocationsRepository
import com.nels.master.appdetail.feature.location.domain.models.Posicion
import java.lang.Exception
import javax.inject.Inject

class GetLocationsBdUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {

    suspend operator fun invoke(): Result{
        return try {
            when(val registerLocation = locationsRepository.getLocationsBd()){
                is LocationsRepository.ResultLocationsBd.Error -> Result.Error(registerLocation.message)
                is LocationsRepository.ResultLocationsBd.Success -> Result.Success(registerLocation.locations)
            }

        }   catch (ex: Exception){
            Result.Error("${ex.message}")
        }
    }

    sealed class Result {
        data class Success(val posiciones: List<Posicion> ) : Result()
        data class Error(val message: String) : Result()
    }
}