package com.example.shop.di

import androidx.lifecycle.SavedStateHandle
import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.FindItemUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.GetItemByIdUseCase
import com.example.shop.domain.useCase.InsertItemUseCase
import com.example.shop.domain.useCase.UpdateItemUseCase
import com.example.shop.presentation.addItemScreen.factory.AddItemViewModelFactory
import com.example.shop.presentation.loadItemScreen.factory.LoadItemsViewModelFactory
import com.example.shop.presentation.loadItemScreen.viewModel.LoadItemsViewModel
import com.example.shop.presentation.updateItemScreen.factory.UpdateItemViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
object ViewModelModule {

    @Provides
    fun provideUpdateItemViewModelFactory(
        updateItemUseCase: UpdateItemUseCase,
        getItemByIdUseCase: GetItemByIdUseCase
    ): UpdateItemViewModelFactory =
        UpdateItemViewModelFactory(updateItemUseCase, getItemByIdUseCase)


    @Provides
    fun provideAddItemViewModelFactory(
        insertItemUseCase: InsertItemUseCase,
        getAllItemsUseCase: GetAllItemsUseCase
    ): AddItemViewModelFactory = AddItemViewModelFactory(insertItemUseCase, getAllItemsUseCase)
}
