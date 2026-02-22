package com.runninsight.data.analysis

import com.runninsight.domain.AnalysisRepository
import com.runninsight.domain.RunAnalysis

class MlKitAnalysisRepository(
    private val mlKitTextRecognizer: MlKitTextRecognizer,
) : AnalysisRepository {

    override suspend fun recognizeText(runAnalysis: RunAnalysis): String {
        return mlKitTextRecognizer.recognize(runAnalysis.imageBytes)
    }
}
