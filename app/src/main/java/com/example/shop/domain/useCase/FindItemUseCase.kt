package com.example.shop.domain.useCase

import com.example.shop.di.AppItem
import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import com.example.shop.presentation.entity.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

interface FindItemUseCase {
    fun invoke(itemList: List<ItemDomain>, itemTitle: String): Flow<List<ItemDomain>>
}

class FindItemUseCaseImpl : FindItemUseCase {

    private val applist = AppItem::fakeStoreItems.get()

    override fun invoke(itemList: List<ItemDomain>, itemTitle: String): Flow<List<ItemDomain>> =
        flow {
            val items = itemList
            val findItemsList = items.filter { it.title.startsWith(itemTitle, true) }
            println("findList ${findItemsList}")
            emit(findItemsList)

        }.flowOn(Dispatchers.IO)
}