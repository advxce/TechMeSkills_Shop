package com.example.shop.data

import com.example.shop.data.entity.toData
import com.example.shop.data.entity.toDomain
import com.example.shop.data.network.FakeStoreApi
import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository

class ItemRepositoryImpl(
    private val fakeStoreApi: FakeStoreApi
) : ItemRepository {
    override suspend fun getAllItems(): List<ItemDomain> {
        return fakeStoreApi.loadItems().map { it.toDomain() }
    }

    override suspend fun insertItem(item: ItemDomain): ItemDomain {
        return fakeStoreApi.addItem(item.toData()).toDomain()
    }

    override suspend fun getItemById(id: Long): ItemDomain {
        return fakeStoreApi.getItemById(id).toDomain()
    }

    override suspend fun deleteItem(id: Long): ItemDomain {
        return fakeStoreApi.deleteItem(id).toDomain()
    }

    override suspend fun updateItem(
        id: Long,
        item: ItemDomain
    ): ItemDomain {
        return fakeStoreApi.updateItem(id, item.toData()).toDomain()
    }

}
