package com.example.shop.di

import android.content.Context
import com.example.shop.utils.NetworkChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    fun provideNetworkChecker(@ApplicationContext context: Context) = NetworkChecker(context)

}