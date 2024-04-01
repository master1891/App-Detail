package com.nels.master.appdetail.feature.location.domain.usecases
import com.nels.master.appdetail.feature.location.domain.RegisterLocationRepository
import com.nels.master.appdetail.feature.location.domain.models.Posicion

import java.lang.Exception
import javax.inject.Inject

class GetRegisterLocationUseCase @Inject constructor(
    private val repository: RegisterLocationRepository
) {

    suspend operator fun invoke(posicion: Posicion): Result{
        return try {
            when(val registerLocation = repository.registerLocation(posicion)){
                is RegisterLocationRepository.ResultRegisterLocation.Error -> Result.Error(registerLocation.message)
                is RegisterLocationRepository.ResultRegisterLocation.Success -> Result.Success
            }

        }   catch (ex: Exception){
            Result.Error("${ex.message}")
        }
    }

    sealed class Result {
        data object Success : Result()
        data class Error(val message: String) : Result()
    }
}