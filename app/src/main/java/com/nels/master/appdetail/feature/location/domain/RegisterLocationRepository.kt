package com.nels.master.appdetail.feature.location.domain

import com.nels.master.appdetail.feature.location.domain.models.Posicion

interface  RegisterLocationRepository {

    suspend fun registerLocation(posicion: Posicion):ResultRegisterLocation

    //manejador de resultados
    sealed class ResultRegisterLocation{
        object Success:ResultRegisterLocation()
        data class Error(val message: String): ResultRegisterLocation()
    }

}