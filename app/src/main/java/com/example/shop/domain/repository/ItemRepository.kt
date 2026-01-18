package com.example.shop.domain.repository

import com.example.shop.domain.entity.ItemDomain

interface ItemRepository {
    suspend fun getAllItems(): List<ItemDomain>
    suspend fun insertItem(item: ItemDomain)
    suspend fun getItemById(id: Long): ItemDomain
    suspend fun deleteItem(item: ItemDomain)
    suspend fun updateItem(id: Long, item: ItemDomain)
}
