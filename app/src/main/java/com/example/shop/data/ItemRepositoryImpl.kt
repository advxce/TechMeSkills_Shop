package com.example.shop.data

import com.example.shop.data.entity.toData
import com.example.shop.data.entity.toDomain
import com.example.shop.data.network.NetworkService
import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository

class ItemRepositoryImpl(
    private val networkService: NetworkService
) : ItemRepository {
    override suspend fun getAllItems(): List<ItemDomain> {
        return networkService.loadItems().map { it.toDomain() }
    }

    override suspend fun insertItem(item: ItemDomain) {
        networkService.addItem(item.toData())
    }

    override suspend fun getItemById(id: Long): ItemDomain? {
        return networkService.getItemById(id)?.toDomain()
    }

}
