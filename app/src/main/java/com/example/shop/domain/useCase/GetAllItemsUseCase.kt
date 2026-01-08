package com.example.shop.domain.useCase

import android.accounts.NetworkErrorException
import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetAllItemsUseCase {
    suspend fun invoke(): List<ItemDomain>
}

class GetAllItemsUseCaseImpl @Inject constructor(
    private val repository: ItemRepository
) : GetAllItemsUseCase {
    override suspend fun invoke(): List<ItemDomain> = withContext(Dispatchers.IO) {
        return@withContext repository.getAllItems()
    }
}

