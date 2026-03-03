package com.runninsight.domain.tracking.repository

import com.runninsight.domain.tracking.model.GeoPoint
import com.runninsight.domain.tracking.model.RunSession
import kotlinx.coroutines.flow.Flow

interface RunRepository {
    fun getLocationUpdates(): Flow<GeoPoint>
    suspend fun startSession(): RunSession
    suspend fun stopSession(sessionId: String): RunSession
}
