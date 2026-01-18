package com.example.shop.domain.entity

data class ItemDomain(
    val id: Long,
    val title: String,
    val marked: Boolean,
    val itemState: ItemState,
    val description: String,
    val image: String,
    val localImage:Int,
)

enum class ItemState {
    LOADING,
    SUCCESS,
    CANCELED,
    ERROR
}
