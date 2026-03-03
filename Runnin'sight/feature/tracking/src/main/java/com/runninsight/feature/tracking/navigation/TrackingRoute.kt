package com.runninsight.feature.tracking.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.runninsight.feature.tracking.ui.TrackingScreen

fun NavGraphBuilder.trackingRoute(navController: NavController) {
    composable("tracking") {
        TrackingScreen(onNavigateToAnalysis = { navController.navigate("analysis") })
    }
}
