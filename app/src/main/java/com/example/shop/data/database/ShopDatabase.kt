package com.example.shop.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shop.data.database.dao.ShopDao
import com.example.shop.data.database.entity.ItemModel

@Database(entities = [ItemModel::class], version = 5)
abstract class ShopDatabase(): RoomDatabase() {
    abstract fun shopDao(): ShopDao

    companion object{
        val MIGRATION_1_2 = object : Migration(1,2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE shop ADD COLUMN localImage INT NOT NULL DEFAULT -1"
                )
            }
        }
    }
}