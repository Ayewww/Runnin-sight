package com.runninsight.domain.usecase

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
