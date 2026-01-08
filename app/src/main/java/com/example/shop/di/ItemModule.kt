package com.example.shop.di

import com.example.shop.data.ItemRepositoryImpl
import com.example.shop.data.entity.ItemData
import com.example.shop.data.network.FakeStoreApi
import com.example.shop.data.network.RetrofitClient
import com.example.shop.domain.repository.ItemRepository
import com.example.shop.domain.useCase.BookmarkItemUseCaseImpl
import com.example.shop.domain.useCase.DeleteItemUseCaseImpl
import com.example.shop.domain.useCase.FindItemUseCase
import com.example.shop.domain.useCase.FindItemUseCaseImpl
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCaseImpl
import com.example.shop.domain.useCase.GetItemByIdUseCaseImpl
import com.example.shop.domain.useCase.InsertItemUseCase
import com.example.shop.domain.useCase.InsertItemUseCaseImpl
import com.example.shop.domain.useCase.UpdateItemUseCaseImpl
//
//class ItemModule {
//
//
//
//    val fakeStoreApi: FakeStoreApi = RetrofitClient.api
//    val itemRepository: ItemRepository = ItemRepositoryImpl(fakeStoreApi)
//
//    val getItemByIdUseCase = GetItemByIdUseCaseImpl(itemRepository)
//    val getItemUseCase: GetAllItemsUseCase = GetAllItemsUseCaseImpl(itemRepository)
//    val insertItemUseCase: InsertItemUseCase = InsertItemUseCaseImpl(itemRepository)
//    val findItemUseCase: FindItemUseCase = FindItemUseCaseImpl(itemRepository)
//    val deleteItemUseCase = DeleteItemUseCaseImpl(itemRepository)
//    val updateItemUseCase = UpdateItemUseCaseImpl(itemRepository)
//    val bookmarkItemUseCase = BookmarkItemUseCaseImpl()
//}