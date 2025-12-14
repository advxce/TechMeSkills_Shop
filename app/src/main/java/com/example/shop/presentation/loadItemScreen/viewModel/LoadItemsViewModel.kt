package com.example.shop.presentation.loadItemScreen.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.presentation.entity.ItemStateUi
import com.example.shop.presentation.entity.ItemUi
import com.example.shop.presentation.entity.toDomain
import com.example.shop.presentation.entity.toUi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadItemsViewModel(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
) : ViewModel() {

    private val _itemListState = MutableLiveData<ItemStateUi>()
    val itemListState: LiveData<ItemStateUi>
        get() = _itemListState

    fun loadItems() {
        _itemListState.value = ItemStateUi.Loading
        viewModelScope.launch(Dispatchers.Main) {
            try {
                delay((500L..1000L).random())
                val items = getAllItemsUseCase.invoke()
                _itemListState.value = ItemStateUi.Success(items.map { it.toUi() })
            } catch (_: CancellationException) {
                _itemListState.value = ItemStateUi.Cancelled("Cancelled loading")
            } catch (_: Exception) {
                _itemListState.value = ItemStateUi.Error("Error with connection")
            }
        }
    }

    fun deleteItems(item: ItemUi) {
        viewModelScope.launch(Dispatchers.Main) {
            val currentList = (_itemListState.value as? ItemStateUi.Success)?.list.orEmpty()
            val updatedList = currentList.filterNot { it.id == item.id }
            _itemListState.value = ItemStateUi.Success(updatedList)
            try {
                deleteItemUseCase.invoke(item.toDomain())
            } catch (_: Exception) {
                _itemListState.value = ItemStateUi.Success(currentList)
                _itemListState.value = ItemStateUi.Error("Cannot delete item")
            }
        }
    }
}
