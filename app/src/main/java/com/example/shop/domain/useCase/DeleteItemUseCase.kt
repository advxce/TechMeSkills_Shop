package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DeleteItemUseCase {
    suspend operator fun invoke(item: ItemDomain)
}

class DeleteItemUseCaseImpl @Inject constructor (
    private val repository: ItemRepository
) : DeleteItemUseCase {
    override suspend fun invoke(item: ItemDomain) = withContext(Dispatchers.IO) {
        repository.deleteItem(item)
    }
}