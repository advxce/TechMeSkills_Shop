package com.example.shop.data.network

import com.example.shop.data.network.model.ItemData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FakeStoreApi {

    @GET("products")
    suspend fun loadItems(): List<ItemData>

    @POST("products")
    suspend fun addItem(@Body item: ItemData): ItemData

    @GET("products/{id}")
    suspend fun getItemById(@Path("id") id: Long): ItemData

    @DELETE("products/{id}")
    suspend fun deleteItem(@Path("id") id: Long): ItemData

    @PUT("products/{id}")
    suspend fun updateItem(@Path("id") id: Long, @Body item: ItemData): ItemData
}