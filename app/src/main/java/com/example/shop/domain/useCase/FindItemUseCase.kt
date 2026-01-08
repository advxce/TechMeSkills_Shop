package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

interface FindItemUseCase {
    fun invoke(itemList: List<ItemDomain>, itemTitle: String): Flow<List<ItemDomain>>
}

class FindItemUseCaseImpl: FindItemUseCase {
    override fun invoke(itemList: List<ItemDomain>, itemTitle: String): Flow<List<ItemDomain>> =
        flow {
            val items = itemList
            val findItemsList = items.filter { it.title.startsWith(itemTitle, true) }
            emit(findItemsList)
        }.flowOn(Dispatchers.IO)
}