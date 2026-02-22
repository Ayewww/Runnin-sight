package com.runninsight.domain.usecase

import com.runninsight.domain.analysis.RunAnalysisScorer
import com.runninsight.domain.model.RunAnalysis
import com.runninsight.domain.repository.AnalysisRepository


class AnalyzeRunResultUseCase(
    private val analysisRepository: AnalysisRepository,
    private val runAnalysisScorer: RunAnalysisScorer,
) {
    suspend operator fun invoke(runAnalysis: RunAnalysis): AnalyzeRunResult {
        val recognizedText = analysisRepository.recognizeText(runAnalysis)
        val score = runAnalysisScorer.score(recognizedText)

        return AnalyzeRunResult(
            recognizedText = recognizedText,
            score = score,
        )
    }
}

data class AnalyzeRunResult(
    val recognizedText: String,
    val score: Int,
)
