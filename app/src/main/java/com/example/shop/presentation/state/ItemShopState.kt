package com.example.shop.presentation.state

import com.example.shop.presentation.entity.ItemUi

sealed class ItemShopState {
    object Loading : ItemShopState()
    object Empty: ItemShopState()
    data class Success(val itemList: List<ItemUi>) : ItemShopState()
    data class Error(val msg: String) : ItemShopState()
}
