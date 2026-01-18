package com.example.shop.di

import com.example.shop.domain.repository.ItemRepository
import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.DeleteItemUseCaseImpl
import com.example.shop.domain.useCase.FindItemUseCase
import com.example.shop.domain.useCase.FindItemUseCaseImpl
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCaseImpl
import com.example.shop.domain.useCase.GetItemByIdUseCase
import com.example.shop.domain.useCase.GetItemByIdUseCaseImpl
import com.example.shop.domain.useCase.InsertItemUseCase
import com.example.shop.domain.useCase.InsertItemUseCaseImpl
import com.example.shop.domain.useCase.UpdateItemUseCase
import com.example.shop.domain.useCase.UpdateItemUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideDeleteItemUseCase(repository: ItemRepository): DeleteItemUseCase =
        DeleteItemUseCaseImpl(repository)

    @Provides
    fun provideGetAllItemsUseCase(repository: ItemRepository): GetAllItemsUseCase =
        GetAllItemsUseCaseImpl(repository)

    @Provides
    fun provideGetItemByIdUseCase(repository: ItemRepository): GetItemByIdUseCase =
        GetItemByIdUseCaseImpl(repository)

    @Provides
    fun provideInsertItemUseCase(repository: ItemRepository): InsertItemUseCase =
        InsertItemUseCaseImpl(repository)

    @Provides
    fun provideUpdateItemUseCase(repository: ItemRepository): UpdateItemUseCase =
        UpdateItemUseCaseImpl(repository)

    @Provides
    fun provideFindItemUseCase(): FindItemUseCase = FindItemUseCaseImpl()

}
