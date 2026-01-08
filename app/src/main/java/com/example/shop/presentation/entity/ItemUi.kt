package com.example.shop.presentation.entity

import android.graphics.drawable.Drawable
import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.entity.ItemState

data class ItemUi(
    val id: Long = 0,
    val title: String,
    val marked: Boolean = false,
    val itemState: ItemState = ItemState.SUCCESS,
    val description: String = "",
    val image: String = "",
    val localImage: Int = -1
)

fun ItemUi.toDomain(): ItemDomain {
    return ItemDomain(
        id = this.id,
        title = this.title,
        marked = this.marked,
        itemState = ItemState.SUCCESS,
        description = description,
        image = image,
        localImage = localImage
    )
}

fun ItemDomain.toUi(): ItemUi {
    return ItemUi(
        id = this.id,
        title = this.title,
        marked = this.marked,
        itemState = this.itemState,
        description = description,
        image = image,
        localImage = localImage
    )
}
