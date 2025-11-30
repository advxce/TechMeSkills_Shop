package com.example.shop.data.memory

import android.content.Context
import com.example.shop.data.entity.ItemData
import com.example.shop.data.entity.toData
import com.example.shop.data.entity.toDomain
import com.example.shop.domain.ItemResources
import com.example.shop.domain.entity.ItemDomain
import kotlinx.serialization.json.Json
import java.io.File

class ItemResourcesImpl(
    private val context: Context
) : ItemResources {

    private fun createResource(): File {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) {
            file.createNewFile()
            return file
        }
        return file
    }

    override fun getAllItemsFromFile(): List<ItemDomain> {
        val file = createResource()
        return try {
            val jsonString = file.readText()
            val jsonFromFile = Json.decodeFromString<List<ItemData>>(jsonString)
            jsonFromFile.map { it.toDomain() }
        } catch (_: Exception) {
            emptyList<ItemDomain>()
        }
    }

    override fun insertAllItemsIntoFile(list: List<ItemDomain>) {
        val file = createResource()
        val jsonString = Json.encodeToString(list.map { it.toData() })
        file.writeText(jsonString)
    }

    override fun insertItemIntoFile(item: ItemDomain) {
        val items = getAllItemsFromFile().toMutableList()
        items.add(item)
        insertAllItemsIntoFile(items)
    }

    companion object {
        const val FILE_NAME = "file.json"
    }
}
