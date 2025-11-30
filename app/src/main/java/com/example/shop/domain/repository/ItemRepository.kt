package com.example.shop.domain.repository

import com.example.shop.domain.entity.ItemDomain

interface ItemRepository {
    fun getAllItems(): List<ItemDomain>
    fun insertItem(item: ItemDomain)
    fun insertAllItemsIntoFile(list: List<ItemDomain>)
    }