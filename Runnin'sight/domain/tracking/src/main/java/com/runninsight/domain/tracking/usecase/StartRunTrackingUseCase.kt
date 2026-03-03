package com.runninsight.domain.tracking.usecase

import com.runninsight.domain.tracking.model.RunSession
import com.runninsight.domain.tracking.repository.RunRepository

class StartRunTrackingUseCase(private val repo: RunRepository) {
    suspend operator fun invoke(): RunSession = repo.startSession()
}
