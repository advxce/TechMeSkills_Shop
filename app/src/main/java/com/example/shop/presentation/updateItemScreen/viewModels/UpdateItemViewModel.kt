package com.example.shop.presentation.updateItemScreen.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shop.di.AppItem
import com.example.shop.domain.useCase.GetItemByIdUseCase
import com.example.shop.domain.useCase.UpdateItemUseCase
import com.example.shop.presentation.entity.ItemStateUi
import com.example.shop.presentation.entity.ItemUi
import com.example.shop.presentation.entity.toDomain
import com.example.shop.presentation.entity.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateItemViewModel @Inject constructor (
    private val updateItemUseCase: UpdateItemUseCase,
): ViewModel() {

    private val listItems = AppItem::fakeStoreItems.get()

    private val _itemState = MutableStateFlow<ItemStateUi>(ItemStateUi.Loading)
    val itemState: StateFlow<ItemStateUi>
        get() = _itemState.asStateFlow()

    fun getItem(id: Long): ItemUi{
        return listItems.first { it.id == id }
    }

    fun loadItem(id: Long){
        viewModelScope.launch(Dispatchers.Main) {
            _itemState.value = ItemStateUi.Loading
            try {
                val currentItem = listItems.first{it.id == id}
                _itemState.value = ItemStateUi.Success(listOf(currentItem))
            } catch (_:Exception){
                _itemState.value = ItemStateUi.Error("Cannot load this item")
            }
        }
    }

    fun updateItem(id: Long, title: String, description: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _itemState.value = ItemStateUi.Loading
            try {
                val currentItem = listItems.first { it.id == id }
                val updatedItem = currentItem.copy(title = title, description = description)
                listItems.replaceAll{ if(it.id == id) updatedItem else it }
                updateItemUseCase.invoke(id, updatedItem.toDomain())

                _itemState.value = ItemStateUi.Success(listOf(updatedItem))
            } catch (_: Exception) {
                _itemState.value = ItemStateUi.Error("Cannot update this item")
            }
        }
    }

}