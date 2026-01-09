package com.example.shop.presentation.loadItemScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.di.AppItem
import com.example.shop.domain.useCase.BookmarkItemUseCase
import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.FindItemUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.presentation.entity.ItemStateUi
import com.example.shop.presentation.entity.toDomain
import com.example.shop.presentation.entity.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadItemsViewModel @Inject constructor(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val findItemUseCase: FindItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val bookmarkItemUseCase: BookmarkItemUseCase
) : ViewModel() {

    private val _itemListState = MutableSharedFlow<ItemStateUi>(
        replay = 1
    )

    private var itemList = AppItem::fakeStoreItems.get()

    val itemListState: SharedFlow<ItemStateUi>
        get() = _itemListState.asSharedFlow()

    private val _searchQuery = MutableSharedFlow<String>(
        replay = 1
    )

    init {
        if (itemList.isNotEmpty()) {
            _itemListState.tryEmit(ItemStateUi.Success(itemList))
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
            try {
                delay((500L..1000L).random())
                getAllItemsUseCase.invoke()
                _itemListState.emit(ItemStateUi.Success(itemList))
                println("app list Load: {${itemList.map { it.id }}}")
            } catch (_: CancellationException) {
                _itemListState.emit(ItemStateUi.Cancelled("Cancelled loading"))
            } catch (_: Exception) {
                _itemListState.emit(ItemStateUi.Error("Error with connection"))
            }
        }
    }

    fun deleteItem(id: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            val deletedItem = deleteItemUseCase.invoke(id).toUi()
            itemList.remove(deletedItem)
            _itemListState.emit(ItemStateUi.Success(itemList))
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
                        _itemListState.emit(ItemStateUi.Success(itemList))
                    } else {
                        searchItems(query)
                    }
                }
        }
    }

    private fun searchItems(itemTitle: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                findItemUseCase.invoke(itemList.map { it.toDomain() }, itemTitle)
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
        viewModelScope.launch(Dispatchers.Main) {
            val currentState = _itemListState.replayCache.lastOrNull()
            println("work")
            if (currentState is ItemStateUi.Success) {
                val updatedItems = currentState.list.map { item ->
                    val marked = bookmarkItemUseCase.invoke(id, item.marked).toUi().marked
                    println("marked $marked")
                    if (item.id == id) {
                        item.copy(marked = marked)
                    } else {
                        item
                    }
                }
                itemList.clear()
                itemList.addAll(updatedItems)
                _itemListState.emit(ItemStateUi.Success(updatedItems))
            } else {
                println("dont work")
            }
        }
    }
}
