package com.runninsight.domain.repository

import com.runninsight.domain.model.RunAnalysis

interface AnalysisRepository {
    suspend fun recognizeText(runAnalysis: RunAnalysis): String
}
