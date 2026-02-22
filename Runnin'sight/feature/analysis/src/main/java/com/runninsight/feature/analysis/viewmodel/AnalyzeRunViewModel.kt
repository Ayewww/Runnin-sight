package com.runninsight.feature.analysis.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runninsight.domain.model.RunAnalysis
import com.runninsight.domain.usecase.AnalyzeRunResultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnalyzeRunViewModel(
    private val analyzeRunResultUseCase: AnalyzeRunResultUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnalyzeRunUiState())
    val uiState: StateFlow<AnalyzeRunUiState> = _uiState.asStateFlow()

    fun analyzeRun(imageBytes: ByteArray, localeTag: String? = null) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            runCatching {
                analyzeRunResultUseCase(
                    RunAnalysis(
                        imageBytes = imageBytes,
                        localeTag = localeTag,
                    ),
                )
            }.onSuccess { result ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        recognizedText = result.recognizedText,
                        score = result.score,
                    )
                }
            }.onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Unknown analysis error",
                    )
                }
            }
        }
    }
}

data class AnalyzeRunUiState(
    val isLoading: Boolean = false,
    val recognizedText: String = "",
    val score: Int? = null,
    val errorMessage: String? = null,
)
