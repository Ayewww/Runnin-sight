package com.runninsight.data.tracking.di

import com.runninsight.data.tracking.datasource.remote.LocationProvider
import com.runninsight.data.tracking.datasource.remote.NaverMapLocationProvider
import com.runninsight.data.tracking.repository.RunRepositoryImpl
import com.runninsight.domain.tracking.repository.RunRepository
import com.runninsight.domain.tracking.usecase.StartRunTrackingUseCase
import com.runninsight.domain.tracking.usecase.StopRunTrackingUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TrackingModule {

    @Binds @Singleton
    abstract fun bindRunRepository(impl: RunRepositoryImpl): RunRepository

    @Binds @Singleton
    abstract fun bindLocationProvider(impl: NaverMapLocationProvider): LocationProvider

    companion object {
        @Provides @Singleton
        fun provideStartRunTrackingUseCase(repo: RunRepository): StartRunTrackingUseCase =
            StartRunTrackingUseCase(repo)

        @Provides @Singleton
        fun provideStopRunTrackingUseCase(repo: RunRepository): StopRunTrackingUseCase =
            StopRunTrackingUseCase(repo)
    }
}
