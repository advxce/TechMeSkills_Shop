package com.example.shop.data.mapper

import com.example.shop.data.database.entity.ItemModel
import com.example.shop.data.network.model.ItemData
import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.entity.ItemState

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
fun ItemModel.toDomain(): ItemDomain {
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
fun ItemDomain.toModel(): ItemModel {
    return ItemModel(
        id = this.id,
        title = this.title,
        marked = this.marked,
        description = this.description,
        image = this.image,
        localImage = localImage
    )
}

fun ItemData.toModel() = ItemModel(
    id = this.id,
    title = this.title,
    marked = this.marked,
    description = this.description,
    image = this.image,
    localImage = -1
)