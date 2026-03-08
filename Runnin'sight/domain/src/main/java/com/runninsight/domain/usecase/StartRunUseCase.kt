package com.runninsight.domain.usecase

import com.runninsight.domain.model.RunSession
import com.runninsight.domain.repository.RunRepository

class StartRunUseCase(
    private val runRepository: RunRepository,
) {
    suspend operator fun invoke(): RunSession = runRepository.startRun()
}
