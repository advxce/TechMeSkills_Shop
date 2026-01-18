package com.example.shop.presentation.loadItemScreen.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.FindItemUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.UpdateItemUseCase
import com.example.shop.presentation.entity.ItemStateUi
import com.example.shop.presentation.entity.ItemUi
import com.example.shop.presentation.entity.toDomain
import com.example.shop.presentation.entity.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoadItemsViewModel @Inject constructor(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val findItemUseCase: FindItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _itemListState = MutableSharedFlow<ItemStateUi>(
        replay = 1
    )
    val itemListState: SharedFlow<ItemStateUi>
        get() = _itemListState.asSharedFlow()

    private val _searchQuery = MutableSharedFlow<String>(
        replay = 1
    )

    private val itemState = savedStateHandle.getStateFlow("item_state", false)

    init {
        if (itemState.value) {
            viewModelScope.launch(Dispatchers.Main) {
                val itemList = getAllItemsUseCase.invoke()
                _itemListState.emit(ItemStateUi.Success(itemList.map { it.toUi() }))
            }
        }
    }

    fun onSearchQuery(query: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _itemListState.emit(ItemStateUi.Loading)
            delay(500)
            _searchQuery.emit(query)
        }
    }

    fun loadItems() {
        _itemListState.tryEmit(ItemStateUi.Loading)
        viewModelScope.launch(Dispatchers.Main) {
            delay(3000)
            try {
                savedStateHandle["item_state"] = true
                println(itemState.value)
                val itemList = getAllItemsUseCase.invoke()
                _itemListState.emit(ItemStateUi.Success(itemList.map { it.toUi() }))
                println(itemList.last())
            } catch (_: CancellationException) {
                _itemListState.emit(ItemStateUi.Cancelled("Cancelled loading"))
            } catch (_: Exception) {
                _itemListState.emit(ItemStateUi.Error("Error with connection"))
            }
        }
    }

    fun deleteItem(itemUi: ItemUi) {
        viewModelScope.launch(Dispatchers.Main) {
           deleteItemUseCase.invoke(itemUi.toDomain())
            val newList = getAllItemsUseCase.invoke()
            _itemListState.emit(ItemStateUi.Success(newList.map { it.toUi() }))
        }
    }

    fun observeSearchFlow() {
        viewModelScope.launch(Dispatchers.Main) {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isNotEmpty() && query.isBlank()) {
                        _itemListState.emit(ItemStateUi.Error("Cant find Item"))
                    } else if (query.isEmpty()) {
                        _itemListState.emit(ItemStateUi.Success(getAllItemsUseCase.invoke().map { it.toUi() }))
                    } else {
                        searchItems(query)
                    }
                }
        }
    }

    private fun searchItems(itemTitle: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                findItemUseCase.invoke(getAllItemsUseCase.invoke().map { it }, itemTitle)
                    .collect { items ->
                        if (items.isEmpty()) {
                            _itemListState.emit(ItemStateUi.Error("Cant find Item"))
                        } else {
                            _itemListState.emit(ItemStateUi.Success(items.map { it.toUi() }))
                        }
                    }
            } catch (_: Exception) {
                _itemListState.emit(ItemStateUi.Error("Problems with searching"))
            }
        }
    }

    fun setItemBookmark(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _itemListState.replayCache.lastOrNull()
            if (currentState is ItemStateUi.Success) {

                val updatedItems = currentState.list.map { item ->
                    val marked = item.marked
                    if (item.id == id) {
                        val updatedItem = item.copy(marked = !marked)
                        updateItemUseCase.invoke(id, updatedItem.toDomain())
                        updatedItem
                    } else {
                        item
                    }
                }
                withContext(Dispatchers.Main) {
                    _itemListState.emit(ItemStateUi.Success(updatedItems))
                }

            }
        }
    }
}
