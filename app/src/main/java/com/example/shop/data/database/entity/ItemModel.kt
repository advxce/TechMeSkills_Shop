package com.example.shop.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop")
data class ItemModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val marked: Boolean,
    val description: String,
    val image: String,
    val localImage: Int,
)