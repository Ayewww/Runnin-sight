package com.runninsight.domain.model

data class RunAnalysis(
    val imageBytes: ByteArray,
    val localeTag: String? = null,
)
