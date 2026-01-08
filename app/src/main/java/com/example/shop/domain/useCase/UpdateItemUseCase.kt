package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UpdateItemUseCase {
    suspend operator fun invoke(id: Long, item: ItemDomain): ItemDomain
}

class UpdateItemUseCaseImpl(
    private val repository: ItemRepository
) : UpdateItemUseCase {
    override suspend fun invoke(
        id: Long,
        item: ItemDomain
    ): ItemDomain = withContext(Dispatchers.IO) {
        return@withContext repository.updateItem(id, item)
    }
}