package com.nels.master.pruebaopenpay.features.locationfeature.viewmodels

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nels.master.appdetail.feature.location.domain.models.Posicion
import com.nels.master.appdetail.feature.location.domain.usecases.GetLocationUsecase
import com.nels.master.appdetail.feature.location.domain.usecases.GetLocationsBdUseCase
import com.nels.master.appdetail.feature.location.domain.usecases.GetRegisterLocationUseCase
import com.nels.master.appdetail.feature.location.presentation.PositionState
import com.nels.master.appdetail.util.LocalNotification



import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewMoldel @Inject constructor(
    private val getLocationUsecase: GetLocationUsecase,
    private val getRegisterLocationUseCase: GetRegisterLocationUseCase,
    private val  getLocationsBdUseCase: GetLocationsBdUseCase,
    @ApplicationContext private val  context: Context
) :ViewModel() {

    val TAG = "MainViewModel"
    private val localNotification = LocalNotification(context)

    var statePosition by mutableStateOf(PositionState())
        private set


    @RequiresApi(Build.VERSION_CODES.O)
    fun requestLocation(evento: PermissionEvent){
        when(evento){
            PermissionEvent.Granted -> {
                viewModelScope.launch {
                    getLocationUsecase.invoke().collect{
                        it?.let {
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            val current = LocalDateTime.now().format(formatter)

                            Log.e(TAG,"Lat: ${it.latitude} Lng: ${it.longitude}")
                            val posicion = Posicion(latitud = it.latitude, longitud = it.longitude, fecha = current )
                            getRegisterLocationUseCase(posicion)
                            localNotification.showNotificaiton("Registro de notificacion",
                                "Lat: ${posicion.latitud},Long: ${posicion.longitud}")
                        }
                    }
                }
            }
            PermissionEvent.Revoked -> {
                Log.e(TAG,"Se quitó el permiso")
            }

            else -> {}
        }
    }

    fun getPositions(){
        viewModelScope.launch {
            val res = getLocationsBdUseCase()
            when(res){
                is GetLocationsBdUseCase.Result.Error -> {
                    statePosition = statePosition.copy(
                        status = PositionEvent.Failure
                    )
                }
                is GetLocationsBdUseCase.Result.Success -> {
                    statePosition = statePosition.copy(
                        status = PositionEvent.Success,
                        positions = res.posiciones
                    )
                }
            }
        }

    }

    sealed interface PermissionEvent {
        data object Granted : PermissionEvent
        data object Revoked : PermissionEvent
    }

    sealed class PositionEvent {
        data object Success : PositionEvent()
        data object Failure : PositionEvent()
        data object Init : PositionEvent()
    }

}