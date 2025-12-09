package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface InsertItemUseCase {
    suspend fun invoke(item: ItemDomain)
}

class InsertItemUseCaseImpl(
    private val repository: ItemRepository
) : InsertItemUseCase {
    override suspend fun invoke(item: ItemDomain) = withContext(Dispatchers.IO) {
        val itemId = getId()
        val newItem = item.copy(id = itemId)
        repository.insertItem(newItem)
    }


    private suspend fun getId(): Long {
        val items = repository.getAllItems()
        val allId = items.map { it.id }.toSet()
        for (i in 1..allId.size) {
            if (!allId.contains(i.toLong())) {
                return i.toLong()
            }
        }

        return items.size.toLong()
    }
}