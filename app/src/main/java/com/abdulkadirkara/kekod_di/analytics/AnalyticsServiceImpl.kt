package com.abdulkadirkara.kekod_di.analytics

import android.util.Log
import javax.inject.Inject

class AnalyticsServiceImpl @Inject constructor() : AnalyticsService {
    override fun analyticsMethods() {
        Log.i("AnalyticsServiceImpl","AnalyticsServiceImpl run")
    }
}