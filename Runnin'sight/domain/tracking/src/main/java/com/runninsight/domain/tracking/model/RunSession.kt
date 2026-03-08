package com.runninsight.domain.tracking.model

data class RunSession(
    val id: String,
    val route: List<GeoPoint> = emptyList(),
    val distanceMeters: Double = 0.0,
    val elapsedSeconds: Long = 0L,
    val isActive: Boolean = true,
)
