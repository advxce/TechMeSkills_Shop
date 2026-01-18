package com.example.shop.di

import android.content.Context
import com.example.shop.utils.NetworkChecker
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
object UtilsModule {

    @Provides
    @Singleton
    fun provideNetworkChecker(context: Context) = NetworkChecker(context)

}