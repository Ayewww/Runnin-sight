package com.runninsight.feature.tracking.presentation

sealed class TrackingUiEvent {
    data object NavigateToAnalysis : TrackingUiEvent()
    data class ShowError(val message: String) : TrackingUiEvent()
}
