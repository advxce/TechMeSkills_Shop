package com.example.shop.presentation.addItemScreen.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.data.entity.toData
import com.example.shop.data.network.NetworkService
import com.example.shop.di.ItemModule
import com.example.shop.domain.entity.ItemState
import com.example.shop.domain.useCase.InsertItemUseCase
import com.example.shop.presentation.entity.ItemUi
import com.example.shop.presentation.entity.toDomain
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

class AddItemViewModel(
    private val context: Context,
    private val insertItemUseCase: InsertItemUseCase
) : ViewModel() {
    private val di = ItemModule(context)

    private val _itemList = MutableLiveData<List<ItemUi>>()
    val itemList: LiveData<List<ItemUi>>
        get() = _itemList

    private val jobMap = ConcurrentHashMap<Long, Job>()

    fun insertItem(itemTitle: String) {
        val itemId = System.currentTimeMillis()
        val updatedItem = ItemUi(id = itemId, title = itemTitle, itemState = ItemState.LOADING)
        _itemList.value = _itemList.value.orEmpty() + updatedItem
        val job = viewModelScope.launch(Dispatchers.Main) {
            try {
                delay((1000L..4000L).random())
                updateState(updatedItem, ItemState.SUCCESS)
                di.networkService.addItem(updatedItem.toDomain().toData())
//                insertItemUseCase.invoke(updatedItem.toDomain())
            } catch (_: CancellationException) {
                updateState(updatedItem, ItemState.CANCELED)
            } catch (_: Exception) {
                updateState(updatedItem, ItemState.ERROR)
            }
        }

        jobMap[itemId] = job
    }

    private fun updateState(item: ItemUi, state: ItemState) {
        val newList = _itemList.value.orEmpty().map {
            if (it.id == item.id) it.copy(itemState = state) else it
        }
        _itemList.value = newList
    }

    fun onCancel(itemId: Long) {
        jobMap[itemId]?.cancel()
    }

    override fun onCleared() {
        jobMap.values.forEach { it.cancel() }
        jobMap.clear()
        super.onCleared()
    }
}
