package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MakeFavoriteItemUseCase {
    suspend fun invoke(item: ItemDomain, mark: Boolean)
}

class MakeFavoriteItemUseCaseImpl(
    private val repository: ItemRepository
) : MakeFavoriteItemUseCase {
    override suspend fun invoke(item: ItemDomain, mark: Boolean) = withContext(Dispatchers.IO) {
        var items = repository.getAllItems().toMutableList()
        val index = items.indexOfFirst { it.id == item.id }
        if (index != -1) {
            val updatedItem = items[index].copy(marked = mark)
            items[index] = updatedItem
            repository.insertAllItems(items)
        }
    }
}
