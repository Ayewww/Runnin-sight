package com.runninsight.data.analysis

import com.runninsight.domain.repository.AnalysisRepository
import com.runninsight.domain.model.RunAnalysis

class MlKitAnalysisRepository(
    private val mlKitTextRecognizer: MlKitTextRecognizer,
) : AnalysisRepository {

    override suspend fun recognizeText(runAnalysis: RunAnalysis): String {
        return mlKitTextRecognizer.recognize(runAnalysis.imageBytes)
    }
}
