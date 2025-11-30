package com.example.shop.presentation.entity

import com.example.shop.domain.entity.ItemDomain

data class ItemUi (
    val id:Int,
    val title:String,
    val marked: Boolean
)

fun ItemUi.toDomain(): ItemDomain{
    return ItemDomain(
        id = this.id,
        title = this.title,
        marked = this.marked
    )
}

fun ItemDomain.toUi(): ItemUi{
    return ItemUi(
        id = this.id,
        title = this.title,
        marked = this.marked
    )
}