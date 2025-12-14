package com.example.shop.di

import android.content.Context
import com.example.shop.data.ItemRepositoryImpl
import com.example.shop.data.memory.ItemResourcesImpl
import com.example.shop.domain.ItemResources
import com.example.shop.domain.repository.ItemRepository
import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.DeleteItemUseCaseImpl

import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCaseImpl
import com.example.shop.domain.useCase.InsertItemUseCase
import com.example.shop.domain.useCase.InsertItemUseCaseImpl
import com.example.shop.domain.useCase.MakeFavoriteItemUseCase
import com.example.shop.domain.useCase.MakeFavoriteItemUseCaseImpl

class ItemModule(context: Context) {
    val itemResources: ItemResources = ItemResourcesImpl(context)
    val itemRepository: ItemRepository = ItemRepositoryImpl(itemResources)

    val getItemUseCase: GetAllItemsUseCase = GetAllItemsUseCaseImpl(itemRepository)
    val insertItemUseCase: InsertItemUseCase = InsertItemUseCaseImpl(itemRepository)
    val makeFavoriteItemUseCase: MakeFavoriteItemUseCase = MakeFavoriteItemUseCaseImpl(itemRepository)

    val deleteItemUseCase: DeleteItemUseCase = DeleteItemUseCaseImpl(itemRepository)
}