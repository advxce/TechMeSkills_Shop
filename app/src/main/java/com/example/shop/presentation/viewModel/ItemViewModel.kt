package com.example.shop.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.InsertItemUseCase
import com.example.shop.domain.useCase.MakeFavoriteItemUseCase
import com.example.shop.presentation.entity.ItemUi
import com.example.shop.presentation.entity.toDomain
import com.example.shop.presentation.entity.toUi

class ItemViewModel(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val insertItemUseCase: InsertItemUseCase,
    private val makeFavoriteItemUseCase: MakeFavoriteItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
) : ViewModel() {
    private var _itemList = MutableLiveData<List<ItemUi>>()
    val itemList: LiveData<List<ItemUi>>
        get() = _itemList



    fun getItems(): List<ItemUi> {
        val items = getAllItemsUseCase.invoke().map { it.toUi() }
        _itemList.value = items
        Log.i("ITEM", _itemList.value.toString())
        return items
    }

    fun insertItem(item: ItemUi) {
        insertItemUseCase.invoke(item.toDomain())
        getItems()
    }

    fun deleteItem(item: ItemUi){
        deleteItemUseCase.invoke(item.toDomain())
        getItems()
    }

    fun changeMarkItem(item: ItemUi, mark: Boolean) {
        makeFavoriteItemUseCase.invoke(item.toDomain(), mark)
    }
}
