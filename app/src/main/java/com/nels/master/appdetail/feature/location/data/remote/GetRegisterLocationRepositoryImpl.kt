package com.nels.master.pruebaopenpay.features.locationfeature.network

import com.google.firebase.firestore.FirebaseFirestore
import com.nels.master.appdetail.feature.location.domain.RegisterLocationRepository
import com.nels.master.appdetail.feature.location.domain.models.Posicion
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class GetRegisterLocationRepositoryImpl @Inject constructor(
    private val firestoreDb: FirebaseFirestore
) : RegisterLocationRepository {
    override suspend fun registerLocation(posicion: Posicion): RegisterLocationRepository.ResultRegisterLocation {

        return suspendCoroutine { continuation ->
            val doc = firestoreDb.collection("locations")
             doc.add(posicion.convertToMap())
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(RegisterLocationRepository.ResultRegisterLocation.Success))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.success(RegisterLocationRepository.ResultRegisterLocation.Error("No se pudo registrar")))
                }
        }
    }

}