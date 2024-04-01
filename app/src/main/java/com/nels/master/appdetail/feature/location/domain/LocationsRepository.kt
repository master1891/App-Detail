package com.nels.master.appdetail.feature.location.domain
import com.nels.master.appdetail.feature.location.domain.models.Posicion

interface  LocationsRepository {

    suspend fun getLocationsBd():ResultLocationsBd

    //manejador de resultados
    sealed class ResultLocationsBd{
        data class Success(val locations: List<Posicion>):ResultLocationsBd()
        data class Error(val message: String): ResultLocationsBd()
    }
}