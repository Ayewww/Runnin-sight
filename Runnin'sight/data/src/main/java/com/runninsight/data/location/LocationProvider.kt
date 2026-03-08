package com.runninsight.data.location


import com.runninsight.domain.model.GeoPoint

interface LocationProvider {
    suspend fun currentLocation(): GeoPoint
}