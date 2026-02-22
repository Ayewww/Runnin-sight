package com.runninsight.domain.analysis

class RunAnalysisScorer {

    fun score(recognizedText: String): Int {
        if (recognizedText.isBlank()) return 0

        val normalized = recognizedText.trim().lowercase()
        var score = 0

        if (containsDistance(normalized)) score += 35
        if (containsPace(normalized)) score += 35
        if (containsDuration(normalized)) score += 30

        return score.coerceIn(0, 100)
    }

    private fun containsDistance(text: String): Boolean {
        val keywords = listOf("km", "kilometer", "distance")
        return keywords.any(text::contains)
    }

    private fun containsPace(text: String): Boolean {
        val keywords = listOf("pace", "min/km", "'", "\"")
        return keywords.any(text::contains)
    }

    private fun containsDuration(text: String): Boolean {
        val keywords = listOf("time", "duration", "hour", "min")
        return keywords.any(text::contains)
    }
}
