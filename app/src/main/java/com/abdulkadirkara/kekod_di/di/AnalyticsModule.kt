package com.abdulkadirkara.kekod_di.di

import com.abdulkadirkara.kekod_di.analytics.AnalyticsService
import com.abdulkadirkara.kekod_di.analytics.AnalyticsServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AnalyticsModule {
    @Binds
    abstract fun bindAnalyticsService(analyticsServiceImpl: AnalyticsServiceImpl) : AnalyticsService
}