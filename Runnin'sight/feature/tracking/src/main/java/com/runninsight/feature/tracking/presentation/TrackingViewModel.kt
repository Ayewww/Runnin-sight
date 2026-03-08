package com.runninsight.feature.tracking.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runninsight.domain.tracking.usecase.StartRunTrackingUseCase
import com.runninsight.domain.tracking.usecase.StopRunTrackingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onFailure
import kotlin.onSuccess
import kotlin.runCatching

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val startRunTracking: StartRunTrackingUseCase,
    private val stopRunTracking: StopRunTrackingUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrackingUiState())
    val uiState: StateFlow<TrackingUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<TrackingUiEvent>(Channel.BUFFERED)
    val uiEvent: Flow<TrackingUiEvent> = _uiEvent.receiveAsFlow()

    fun startTracking() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            runCatching { startRunTracking() }
                .onSuccess { session -> _uiState.update { it.copy(isTracking = true, session = session, isLoading = false) } }
                .onFailure { e -> _uiEvent.send(TrackingUiEvent.ShowError(e.message ?: "오류")); _uiState.update { it.copy(isLoading = false) } }
        }
    }

    fun stopTracking() {
        val sessionId = _uiState.value.session?.id ?: return
        viewModelScope.launch {
            runCatching { stopRunTracking(sessionId) }
                .onSuccess { _uiState.update { it.copy(isTracking = false, session = null) }; _uiEvent.send(TrackingUiEvent.NavigateToAnalysis) }
                .onFailure { e -> _uiEvent.send(TrackingUiEvent.ShowError(e.message ?: "오류")) }
        }
    }
}
