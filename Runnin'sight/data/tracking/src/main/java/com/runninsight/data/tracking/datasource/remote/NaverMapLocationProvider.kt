package com.runninsight.data.tracking.datasource.remote

import com.runninsight.domain.tracking.model.GeoPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NaverMapLocationProvider @Inject constructor() : LocationProvider {
    // TODO: Naver Map SDK 실제 위치 연동
    override fun getLocationUpdates(): Flow<GeoPoint> = flow {
        emit(GeoPoint(37.5665, 126.9780)) // 서울 기본값
    }
}
