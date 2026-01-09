package com.example.shop.presentation.entity

sealed class ItemStateUi {
    object Loading : ItemStateUi()
    data class Cancelled(val message:String) : ItemStateUi()
    data class Success(val list: List<ItemUi>) : ItemStateUi()
    data class Error(val message: String) : ItemStateUi()
}
