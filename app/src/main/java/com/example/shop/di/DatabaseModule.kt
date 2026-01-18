package com.example.shop.di

import android.content.Context
import androidx.room.Room
import com.example.shop.data.database.ShopDatabase
import com.example.shop.data.database.dao.ShopDao
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module

object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase( context: Context): ShopDatabase = Room.databaseBuilder(
        context,
        ShopDatabase::class.java,
        "shop_database"
    ).addMigrations(ShopDatabase.MIGRATION_1_2)
        .build()

    @Provides
    @Singleton
    fun provideShopDao(db: ShopDatabase): ShopDao = db.shopDao()
}