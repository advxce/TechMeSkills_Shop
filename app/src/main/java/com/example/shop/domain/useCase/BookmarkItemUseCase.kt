package com.example.shop.domain.useCase

import com.example.shop.di.AppItem
import com.example.shop.domain.entity.ItemDomain
import com.example.shop.presentation.entity.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface BookmarkItemUseCase {
    suspend operator fun invoke(id: Long, isMarked: Boolean): ItemDomain
}

class BookmarkItemUseCaseImpl : BookmarkItemUseCase {
    override suspend fun invoke(id: Long, isMarked: Boolean): ItemDomain = withContext(Dispatchers.IO) {
        val items = AppItem::fakeStoreItems.get()
        val currentItem = items.first { it.id == id }
        val updatedCurrentItem = currentItem.copy(marked = !isMarked)
        return@withContext updatedCurrentItem.toDomain()
    }
}
