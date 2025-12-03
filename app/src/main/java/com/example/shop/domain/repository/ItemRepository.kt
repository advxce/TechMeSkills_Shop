package com.example.shop.domain.repository

import com.example.shop.domain.entity.ItemDomain

interface ItemRepository {
    fun getAllItems(): List<ItemDomain>
    fun insertItem(item: ItemDomain)
    fun insertAllItems(list: List<ItemDomain>)
    fun deleteItem(item: ItemDomain)
}