package com.runninsight.data.repository

import com.runninsight.data.location.LocationProvider
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RunRepositoryImpl(
    private val locationProvider: LocationProvider,
) : RunRepository {

    private val activeSession = MutableStateFlow<RunSession?>(null)

    override suspend fun startRun(): RunSession {
        check(activeSession.value == null) { "A run is already active." }

        val start = locationProvider.currentLocation()
        return RunSession(
            id = UUID.randomUUID().toString(),
            startLocation = start,
            isActive = true,
        ).also { activeSession.value = it }
    }

    override suspend fun stopRun(): RunSession {
        val session = requireNotNull(activeSession.value) { "No active run to stop." }
        val end = locationProvider.currentLocation()
        return session
            .complete(end)
            .also { activeSession.value = null }
    }

    override fun observeActiveRun(): Flow<RunSession?> = activeSession.asStateFlow()

    private fun RunSession.complete(end: GeoPoint): RunSession = copy(
        endLocation = end,
        isActive = false,
    )
}
