package com.example.shop.data.entity

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.entity.ItemState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemData(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    val marked: Boolean = false,
    val price: Double = 0.0,
    val description: String? = null,
    val category: String? = null,
    val image: String? = null
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
