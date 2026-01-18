package com.example.shop.presentation.addItemScreen.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.domain.entity.ItemState
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.InsertItemUseCase
import com.example.shop.presentation.entity.ItemUi
import com.example.shop.presentation.entity.toDomain
import com.example.shop.presentation.entity.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val insertItemUseCase: InsertItemUseCase,
    private val getAllItemsUseCase: GetAllItemsUseCase
) : ViewModel() {

    private val _itemList = MutableLiveData<List<ItemUi>>()
    val itemList: LiveData<List<ItemUi>>
        get() = _itemList

    private val jobMap = ConcurrentHashMap<Long, Job>()

    fun insertItem(itemTitle: String, itemDescription:String, itemImage: Int) {
        val itemId = System.currentTimeMillis()
        val updatedItem = ItemUi(
            id = itemId,
            title = itemTitle,
            description = itemDescription,
            itemState = ItemState.LOADING,
            localImage = itemImage
        )
        _itemList.value = _itemList.value.orEmpty() + updatedItem
        val job = viewModelScope.launch(Dispatchers.Main) {
            try {
                delay((1000L..4000L).random())
                updateState(updatedItem, ItemState.SUCCESS)
                println("add" + updatedItem)
                val addedItem = insertItemUseCase.invoke(updatedItem.toDomain())
                println("add" + addedItem)
                val itemList = getAllItemsUseCase.invoke()
                println("add ${itemList.map { it.localImage }}")
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
