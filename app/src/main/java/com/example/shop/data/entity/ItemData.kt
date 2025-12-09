package com.example.shop.data.entity

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.entity.ItemState
import kotlinx.serialization.Serializable

@Serializable
data class ItemData(
    val id: Long,
    val title: String,
    val marked: Boolean
)

fun ItemDomain.toData(): ItemData {
    return ItemData(
        id = this.id,
        title = this.title,
        marked = this.marked
    )
}

fun ItemData.toDomain(): ItemDomain {
    return ItemDomain(
        id = this.id,
        title = this.title,
        marked = this.marked,
        itemState = ItemState.SUCCESS
    )
}
