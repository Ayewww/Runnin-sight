package com.runninsight.domain.repository

interface AnalysisRepository {
    suspend fun recognizeText(runAnalysis: RunAnalysis): String
}
