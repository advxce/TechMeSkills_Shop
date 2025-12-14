package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DeleteItemUseCase{
    suspend fun invoke(item: ItemDomain)
}

class DeleteItemUseCaseImpl(
    private val repository: ItemRepository
): DeleteItemUseCase {
    override suspend fun invoke(item: ItemDomain) = withContext(Dispatchers.IO) {
        repository.deleteItem(item)
    }
}