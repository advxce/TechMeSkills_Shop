package com.example.shop.di

import android.content.Context
import androidx.room.Room
import com.example.shop.data.database.ShopDatabase
import com.example.shop.data.database.dao.ShopDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ShopDatabase = Room.databaseBuilder(
        context,
        ShopDatabase::class.java,
        "shop_database"
    ).addMigrations(ShopDatabase.MIGRATION_1_2)
        .build()

    @Provides
    fun provideShopDao(db: ShopDatabase): ShopDao = db.shopDao()
}