package com.example.shop.domain

import com.example.shop.domain.entity.ItemDomain

interface ItemResources {
    fun insertItemIntoFile(item: ItemDomain)
    fun getAllItemsFromFile(): List<ItemDomain>
    fun insertAllItemsIntoFile(list: List<ItemDomain>)
    fun deleteItemFromFile(item: ItemDomain)
}
