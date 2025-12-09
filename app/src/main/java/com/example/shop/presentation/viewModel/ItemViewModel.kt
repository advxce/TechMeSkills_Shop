package com.example.shop.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.domain.entity.ItemState
import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.InsertItemUseCase
import com.example.shop.domain.useCase.MakeFavoriteItemUseCase
import com.example.shop.presentation.entity.ItemUi
import com.example.shop.presentation.entity.toDomain
import com.example.shop.presentation.entity.toUi
import com.example.shop.presentation.state.ItemShopState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

class ItemViewModel(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val insertItemUseCase: InsertItemUseCase,
    private val makeFavoriteItemUseCase: MakeFavoriteItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
) : ViewModel() {

    private var _itemListState = MutableLiveData<ItemShopState>(ItemShopState.Empty)
    val itemListState: LiveData<ItemShopState>
        get() = _itemListState

    private var _itemList = MutableLiveData<List<ItemUi>>()
    val itemList: LiveData<List<ItemUi>>
        get() = _itemList

    private var job: Job? = null

    private val jopMap = ConcurrentHashMap<Long, Job>()

    fun getItemsWithState() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.Main) {
            _itemListState.value = ItemShopState.Loading
            try {
                val items = getAllItemsUseCase.invoke()

                _itemListState.value = ItemShopState.Success(items.map { it.toUi() })
            } catch (_: CancellationException) {
                println("Cancel Loading")
            } catch (_: Exception) {
                _itemListState.value = ItemShopState.Error("Problems with connection")
            }
        }
    }

    fun cancelLoading() {
        job?.cancel()
        _itemListState.value = ItemShopState.Error("Cancel Loading")
    }

    fun insertItem(newItem: ItemUi) {

        val itemId = System.currentTimeMillis()
        val updateItem = newItem.copy(id = itemId, itemState = ItemState.LOADING)
        updatedItemList(updateItem)

        val job: Job = viewModelScope.launch(Dispatchers.IO) {
            try {
                delay((1000L..4000L).random())
                insertItemUseCase.invoke(newItem.toDomain())
                withContext(Dispatchers.Main) {
                    updateItemState(0, ItemState.SUCCESS)
                }


            } catch (e: CancellationException) {

                withContext(Dispatchers.Main) {
                    updateItemState(0, ItemState.CANCELED)
                }


            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    updateItemState(0, ItemState.ERROR)
                }

            }
        }
        jopMap[newItem.id] = job
    }

    private fun updateItemState(id: Long, state: ItemState) {

        val updated = _itemList.value.orEmpty().map {
            if (it.id == id) it.copy(itemState = state) else it
        }
        _itemList.value = updated

    }

    private fun updatedItemList(item: ItemUi) {
            val newList = _itemList.value.orEmpty().toMutableList()
            newList.add(item)
            _itemList.postValue(newList.map { it })



    }

    fun deleteItem(item: ItemUi) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                deleteItemUseCase.invoke(item.toDomain())
                getItemsWithState()
            } catch (_: Exception) {
                _itemListState.value = ItemShopState.Error("Cannot delete item")
            }
        }
    }

    fun changeMarkItem(item: ItemUi, mark: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                makeFavoriteItemUseCase.invoke(item.toDomain(), mark)
            } catch (_: Exception) {
                _itemListState.value = ItemShopState.Error("Cannot mark item")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelLoading()
    }
}
