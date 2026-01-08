package com.example.shop.di

import com.example.shop.data.ItemRepositoryImpl
import com.example.shop.domain.repository.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(impl: ItemRepositoryImpl): ItemRepository

}