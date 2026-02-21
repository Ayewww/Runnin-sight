package com.runninsight.data.location


interface LocationProvider {
    suspend fun currentLocation(): GeoPoint
}