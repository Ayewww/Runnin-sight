package com.runninsight.domain.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO

/**
 * Domain entity representing a single running session lifecycle.
 */
data class RunSession(
    val id: String,
    val startLocation: GeoPoint,
    val endLocation: GeoPoint? = null,
    val distanceMeters: Double = 0.0,
    val elapsedTime: Duration = ZERO,
    val isActive: Boolean = true,
)
