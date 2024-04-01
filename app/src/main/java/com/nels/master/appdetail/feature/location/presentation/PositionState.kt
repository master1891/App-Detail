package com.nels.master.appdetail.feature.location.presentation


import com.nels.master.appdetail.feature.location.domain.models.Posicion
import com.nels.master.pruebaopenpay.features.locationfeature.viewmodels.MainViewMoldel

data class PositionState(
    val positions: List<Posicion> =  emptyList(),
    val status: MainViewMoldel.PositionEvent = MainViewMoldel.PositionEvent.Init
)