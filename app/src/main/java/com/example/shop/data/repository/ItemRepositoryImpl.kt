package com.example.shop.data.repository

import com.example.shop.data.database.dao.ShopDao
import com.example.shop.data.mapper.toDomain
import com.example.shop.data.mapper.toModel
import com.example.shop.data.network.FakeStoreApi
import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import com.example.shop.utils.NetworkChecker
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val fakeStoreApi: FakeStoreApi,
    private val shopDao: ShopDao,
    private val networkChecker: NetworkChecker,
) : ItemRepository {
    override suspend fun getAllItems(): List<ItemDomain> {
        if (!networkChecker.hasInternetConnection()) {
            return shopDao.loadItems().map { it.toDomain() }
        }
        if (shopDao.loadItems().isEmpty()) {
            val localList = fakeStoreApi.loadItems().map { it.toModel() }
            shopDao.insertItems(localList)
        }
        return shopDao.loadItems().map { it.toDomain() }
    }

    override suspend fun insertItem(item: ItemDomain) {
        return shopDao.addItem(item.toModel())
    }

    override suspend fun getItemById(id: Long): ItemDomain {
        return shopDao.getItemById(id).toDomain()
    }

    override suspend fun deleteItem(item: ItemDomain) {
        return shopDao.deleteItem(item.toModel())
    }

    override suspend fun updateItem(
        id: Long,
        item: ItemDomain
    ) {
        return shopDao.updateItem(
            id,
            item.title,
            item.marked,
            item.description,
            item.image
        )
    }
}
