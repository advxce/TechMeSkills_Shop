package com.example.shop.domain.entity


data class ItemDomain(
    val id:Long,
    val title:String,
    val marked: Boolean,
    val itemState: ItemState
)

enum class ItemState{
    LOADING,
    SUCCESS,
    CANCELED,
    ERROR
}