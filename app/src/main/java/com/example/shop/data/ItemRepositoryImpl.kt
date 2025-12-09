package com.example.shop.data

import com.example.shop.domain.ItemResources
import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository

class ItemRepositoryImpl(
    private val itemResources: ItemResources
) : ItemRepository {
    override suspend fun getAllItems(): List<ItemDomain> {
        return itemResources.getAllItemsFromFile()
    }

    override suspend fun insertItem(item: ItemDomain) {
        itemResources.insertItemIntoFile(item)
    }

    override suspend fun insertAllItems(list: List<ItemDomain>) {
        itemResources.insertAllItemsIntoFile(list = list)
    }

    override suspend fun deleteItem(item: ItemDomain) {
        itemResources.deleteItemFromFile(item)
    }
}
