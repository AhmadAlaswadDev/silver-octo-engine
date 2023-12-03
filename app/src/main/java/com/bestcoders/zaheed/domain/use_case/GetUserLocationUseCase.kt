package com.bestcoders.zaheed.domain.use_case

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.bestcoders.zaheed.core.util.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.bestcoders.zaheed.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@SuppressLint("MissingPermission")
class GetUserLocationUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(): Flow<Resource<Location>> = flow {
        emit(Resource.Loading())
        return@flow try {
            val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            val location = fusedLocationClient.lastLocation.await()
            if (location != null) {
                emit(Resource.Success(location))
            } else {
                emit(Resource.Error("Failed to get user location, make sure permissions granted and GPS enabled"))
            }
        } catch (e: Exception) {
            return@flow emit(
                Resource.Error(context.getString(R.string.location_permission_required))
            )
        }
    }
}
