package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface InsertItemUseCase {
    suspend fun invoke(item: ItemDomain): ItemDomain
}

class InsertItemUseCaseImpl @Inject constructor(
    private val repository: ItemRepository
) : InsertItemUseCase {
    override suspend fun invoke(item: ItemDomain): ItemDomain = withContext(Dispatchers.IO) {
       return@withContext repository.insertItem(item)
    }
}