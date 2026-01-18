package com.example.shop.di

import com.example.shop.data.repository.ItemRepositoryImpl
import com.example.shop.domain.repository.ItemRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(impl: ItemRepositoryImpl): ItemRepository

}