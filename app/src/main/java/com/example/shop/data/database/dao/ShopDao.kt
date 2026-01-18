package com.example.shop.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shop.data.database.entity.ItemModel

@Dao
interface ShopDao {

    @Query("SELECT * FROM shop")
    suspend fun loadItems(): List<ItemModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item: ItemModel)

    @Query("SELECT * FROM shop WHERE id = :id")
    suspend fun getItemById(id: Long): ItemModel

    @Delete
    suspend fun deleteItem(item: ItemModel)

    @Query(
        "UPDATE shop " +
            "SET title = :title, marked = :marked, " +
            "description = :description, image = :image " +
            "WHERE id = :id"
    )
    suspend fun updateItem(
        id: Long,
        title: String,
        marked: Boolean,
        description: String,
        image: String
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(list: List<ItemModel>)
}
