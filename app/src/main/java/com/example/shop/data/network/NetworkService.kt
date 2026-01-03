package com.example.shop.data.network

import com.example.shop.data.entity.ItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class NetworkService {

    suspend fun loadItems(): List<ItemData> = withContext(Dispatchers.IO) {
        var connection: HttpURLConnection? = null
        return@withContext try {
            val url = URL(BASE_URL + "products")
            connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 5000
                readTimeout = 5000
            }
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val responseText = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonArray = JSONArray(responseText)
                val items = mutableListOf<ItemData>()
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val item = ItemData(
                        id = obj.getLong("id"),
                        title = obj.getString("title")
                    )
                    items.add(item)
                }
                items
            } else {
                emptyList()
            }
        } catch (_: Exception) {
            emptyList()
        } finally {
            connection?.disconnect()
        }
    }

    suspend fun addItem(item: ItemData): ItemData? = withContext(Dispatchers.IO) {
        var connection: HttpURLConnection? = null
        return@withContext try {
            val url = URL(BASE_URL + "products")

            val jsonBody = JSONObject().apply {
                put("title", item.title)
                put("price", item.price.takeIf { it != 0.0 } ?: 9.99)
                put("description", item.description ?: "New item added")
                put("category", item.category ?: "electronics")
                put("image", item.image ?: "https://i.pravatar.cc")
            }.toString()
            connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                connectTimeout = 5000
                readTimeout = 5000
                doOutput = true
                setRequestProperty("Content-Type", "application/json; charset=utf-8")
            }

            connection.outputStream.use { os ->
                OutputStreamWriter(os, Charsets.UTF_8).use { writer ->
                    writer.write(jsonBody)
                    writer.flush()
                }
            }

            val responseCode = connection.responseCode
            val responseText = (
                if (responseCode in 200..299) {
                    connection.inputStream
                } else {
                    connection.errorStream
                }
                )?.bufferedReader()?.use { it.readText() }

            if (responseCode in 200..299 && responseText != null) {
                val jsonResponse = JSONObject(responseText)

                ItemData(
                    id = jsonResponse.getLong("id"),
                    title = jsonResponse.getString("title"),
                    price = jsonResponse.optDouble("price", 0.0),
                    description = jsonResponse.optString("description", ""),
                    category = jsonResponse.optString("category", ""),
                    image = jsonResponse.optString("image", "")
                )
            } else {
                null
            }
        } catch (_: Exception) {
            null
        } finally {
            connection?.disconnect()
        }
    }

    suspend fun getItemById(id: Long): ItemData? = withContext(Dispatchers.IO) {
        var connection: HttpURLConnection? = null
        return@withContext try {
            val url = URL(BASE_URL + "products/$id")
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                readTimeout = 5000
                connectTimeout = 5000
            }

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val requestText = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonObject = JSONObject(requestText)
                val id = jsonObject.getLong("id")
                val title = jsonObject.getString("title")
                val item = ItemData(id = id, title = title)
                return@withContext item
            } else {
                null
            }
        } catch (_: Exception) {
            null
        } finally {
            connection?.disconnect()
        }
    }

    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
    }
}
