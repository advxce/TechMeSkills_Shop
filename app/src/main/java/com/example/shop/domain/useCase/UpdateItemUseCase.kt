package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UpdateItemUseCase {
    suspend operator fun invoke(id: Long, item: ItemDomain)
}

class UpdateItemUseCaseImpl @Inject constructor(
    private val repository: ItemRepository
) : UpdateItemUseCase {
    override suspend fun invoke(
        id: Long,
        item: ItemDomain
    ) = withContext(Dispatchers.IO) {
        repository.updateItem(id, item)
    }
}