package com.example.shop.data.entity

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.entity.ItemState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class ItemData(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    val marked: Boolean = false,
    val price: Double = 0.0,
    @SerialName("description") val description: String,
    val category: String? = null,
    @SerialName("image") val image: String
)

fun ItemDomain.toData(): ItemData {
    return ItemData(
        id = this.id,
        title = this.title,
        marked = this.marked,
        description = this.description,
        image = this.image
    )
}

fun ItemData.toDomain(): ItemDomain {
    return ItemDomain(
        id = this.id,
        title = this.title,
        marked = this.marked,
        itemState = ItemState.SUCCESS,
        description = description,
        image = image,
        -1
    )
}
