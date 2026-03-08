package com.runninsight.data.tracking.repository

import com.runninsight.data.tracking.datasource.remote.LocationProvider
import com.runninsight.domain.tracking.model.GeoPoint
import com.runninsight.domain.tracking.model.RunSession
import com.runninsight.domain.tracking.repository.RunRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class RunRepositoryImpl @Inject constructor(
    private val locationProvider: LocationProvider,
) : RunRepository {
    override fun getLocationUpdates(): Flow<GeoPoint> = locationProvider.getLocationUpdates()
    override suspend fun startSession(): RunSession =
        RunSession(id = UUID.randomUUID().toString())
    override suspend fun stopSession(sessionId: String): RunSession =
        RunSession(id = sessionId, isActive = false)
}
