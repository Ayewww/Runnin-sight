package com.runninsight.feature.tracking.presentation

import com.runninsight.domain.tracking.model.GeoPoint
import com.runninsight.domain.tracking.model.RunSession

data class TrackingUiState(
    val isTracking: Boolean = false,
    val currentLocation: GeoPoint? = null,
    val session: RunSession? = null,
    val isLoading: Boolean = false,
)
