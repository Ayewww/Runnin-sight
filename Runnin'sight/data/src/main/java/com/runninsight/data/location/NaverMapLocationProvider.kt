package com.runninsight.data.location

/**
 * Adapter boundary for Naver Map SDK based location retrieval.
 *
 * Keep any concrete Naver Map SDK classes referenced/implemented in this data layer.
 */
class NaverMapLocationProvider(
    private val locationFetcher: suspend () -> GeoPoint,
) : LocationProvider {
    override suspend fun currentLocation(): GeoPoint = locationFetcher()
}
