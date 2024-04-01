package com.nels.master.appdetail.feature.location.domain.models

data class Posicion(
    val latitud:Double,
    val longitud:Double,
    val fecha:String
){
   fun convertToMap():Map<String,Any?>{
       return mapOf(
           "latitud" to latitud,
           "longitud" to longitud,
           "fecha" to fecha
       )
    }
}