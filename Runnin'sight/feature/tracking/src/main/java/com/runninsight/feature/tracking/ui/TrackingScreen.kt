package com.runninsight.feature.tracking.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.runninsight.feature.tracking.presentation.TrackingUiEvent
import com.runninsight.feature.tracking.presentation.TrackingViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TrackingScreen(
    onNavigateToAnalysis: () -> Unit,
    viewModel: TrackingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is TrackingUiEvent.NavigateToAnalysis -> onNavigateToAnalysis()
                is TrackingUiEvent.ShowError -> { }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(if (uiState.isTracking) "러닝 중..." else "준비", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { if (uiState.isTracking) viewModel.stopTracking() else viewModel.startTracking() },
            enabled = !uiState.isLoading,
        ) {
            Text(if (uiState.isTracking) "중지" else "시작")
        }
    }
}
