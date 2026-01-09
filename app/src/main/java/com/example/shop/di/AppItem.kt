package com.example.shop.di

import com.example.shop.data.entity.ItemData
import com.example.shop.presentation.entity.ItemUi

object AppItem {
    val fakeStoreItems: MutableList<ItemUi> by lazy {
        mutableListOf<ItemUi>()
    }
}
