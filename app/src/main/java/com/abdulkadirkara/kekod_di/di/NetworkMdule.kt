package com.abdulkadirkara.kekod_di.di

import com.abdulkadirkara.kekod_di.network.AuthInterceptor
import com.abdulkadirkara.kekod_di.network.OtherInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object NetworkMdule {
    @AuthInterceptorRetrofit
    @Provides
    fun provideAuthRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            //.addInterceptor(AuthInterceptor())
            .build() // build dediğimizde bu bie retrofit'i veriyor.
    }

    @OtherInterceptorRetrofit
    @Provides
    fun provideOtherRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            //.addInterceptor(OtherInterceptor())
            .build() // build dediğimizde bu bie retrofit'i veriyor.
    }
}