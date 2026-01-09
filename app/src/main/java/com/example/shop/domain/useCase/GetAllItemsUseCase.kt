package com.example.shop.domain.useCase

import com.example.shop.di.AppItem
import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import com.example.shop.presentation.entity.toDomain
import com.example.shop.presentation.entity.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetAllItemsUseCase {
    suspend fun invoke(): List<ItemDomain>
}

class GetAllItemsUseCaseImpl @Inject constructor(
    private val repository: ItemRepository
) : GetAllItemsUseCase {
    var appList = AppItem::fakeStoreItems.get()
    override suspend fun invoke(): List<ItemDomain> = withContext(Dispatchers.IO) {
        return@withContext if (appList.isEmpty()) {
            appList.addAll(repository.getAllItems().map { it.toUi() })
            println("getItems ${appList.map { it.id }}")
            appList.map { it.toDomain() }
        } else {
            appList.map { it.toDomain() }
        }
    }
}
