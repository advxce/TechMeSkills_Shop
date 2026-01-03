package com.example.shop.di

import com.example.shop.data.ItemRepositoryImpl
import com.example.shop.data.network.NetworkService
import com.example.shop.domain.repository.ItemRepository
import com.example.shop.domain.useCase.FindItemUseCase
import com.example.shop.domain.useCase.FindItemUseCaseImpl
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCaseImpl
import com.example.shop.domain.useCase.GetItemByIdUseCaseImpl
import com.example.shop.domain.useCase.InsertItemUseCase
import com.example.shop.domain.useCase.InsertItemUseCaseImpl

class ItemModule {
    val networkService: NetworkService = NetworkService()
    val itemRepository: ItemRepository = ItemRepositoryImpl(networkService)

    val getItemByIdUseCase = GetItemByIdUseCaseImpl(itemRepository)
    val getItemUseCase: GetAllItemsUseCase = GetAllItemsUseCaseImpl(itemRepository)
    val insertItemUseCase: InsertItemUseCase = InsertItemUseCaseImpl(itemRepository)
    val findItemUseCase: FindItemUseCase = FindItemUseCaseImpl(itemRepository)

}