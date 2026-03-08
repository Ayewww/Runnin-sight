package com.runninsight.data.tracking.datasource.remote

import com.runninsight.domain.tracking.model.GeoPoint
import kotlinx.coroutines.flow.Flow

interface LocationProvider {
    fun getLocationUpdates(): Flow<GeoPoint>
}
