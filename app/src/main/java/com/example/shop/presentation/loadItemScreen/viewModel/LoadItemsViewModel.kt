package com.example.shop.presentation.loadItemScreen.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.FindItemUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.MakeFavoriteItemUseCase
import com.example.shop.presentation.entity.ItemStateUi
import com.example.shop.presentation.entity.ItemUi
import com.example.shop.presentation.entity.toDomain
import com.example.shop.presentation.entity.toUi
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

class LoadItemsViewModel(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val findItemUseCase: FindItemUseCase,
    private val makeFavoriteItemUseCase: MakeFavoriteItemUseCase,
) : ViewModel() {

    private val _itemListState = MutableSharedFlow<ItemStateUi>(
        replay = 1
    )

    val itemListState: SharedFlow<ItemStateUi>
        get() = _itemListState.asSharedFlow()

    private val _searchQuery = MutableSharedFlow<String>(
        replay = 1
    )

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
                val items = getAllItemsUseCase.invoke()
                _itemListState.emit(ItemStateUi.Success(items.map { it.toUi() }))
            } catch (_: CancellationException) {
                _itemListState.emit(ItemStateUi.Cancelled("Cancelled loading"))
            } catch (_: Exception) {
                _itemListState.emit(ItemStateUi.Error("Error with connection"))
            }
        }
    }

    fun deleteItems(item: ItemUi) {
        viewModelScope.launch(Dispatchers.Main) {
            val currentList =
                (_itemListState.replayCache.lastOrNull() as? ItemStateUi.Success)?.list.orEmpty()
            val updatedList = currentList.filterNot { it.id == item.id }
            _itemListState.emit(ItemStateUi.Success(updatedList))
            try {
                deleteItemUseCase.invoke(item.toDomain())
            } catch (_: Exception) {
                _itemListState.emit(ItemStateUi.Success(currentList))
                _itemListState.emit(ItemStateUi.Error("Cannot delete item"))
            }
        }
    }

    fun observeSearchFlow() {
        viewModelScope.launch(Dispatchers.Main) {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isBlank()) {
                        _itemListState.emit(ItemStateUi.Error("Cant find Item"))
                    } else {
                        searchItems(query)
                    }
                }
        }
    }

    private fun searchItems(itemTitle: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                findItemUseCase.invoke(itemTitle)
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

    fun setCheck(item: ItemUi) {
        viewModelScope.launch(Dispatchers.Main) {
            makeFavoriteItemUseCase.invoke(item.toDomain(), !item.marked)
            val updatedList = getAllItemsUseCase.invoke()
            _itemListState.emit(ItemStateUi.Success(updatedList.map { it.toUi() }))
        }
    }
}
