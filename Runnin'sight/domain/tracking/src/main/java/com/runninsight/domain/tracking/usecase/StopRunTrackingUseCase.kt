package com.runninsight.domain.tracking.usecase

import com.runninsight.domain.tracking.model.RunSession
import com.runninsight.domain.tracking.repository.RunRepository

class StopRunTrackingUseCase(private val repo: RunRepository) {
    suspend operator fun invoke(sessionId: String): RunSession = repo.stopSession(sessionId)
}
