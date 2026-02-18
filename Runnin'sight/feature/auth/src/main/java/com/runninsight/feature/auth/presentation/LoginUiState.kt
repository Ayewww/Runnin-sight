package com.runninsight.feature.auth.presentation

import com.runninsight.domain.auth.AuthUser

sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data class Success(val user: AuthUser) : LoginUiState
    data class Error(val message: String) : LoginUiState
}
