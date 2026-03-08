package com.runninsight.domain.repository

import com.runninsight.domain.model.RunSession
import kotlinx.coroutines.flow.Flow

interface RunRepository {
    suspend fun startRun(): RunSession
    suspend fun stopRun(): RunSession
    fun observeActiveRun(): Flow<RunSession?>
}
