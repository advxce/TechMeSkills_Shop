package com.example.shop.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.InsertItemUseCase
import com.example.shop.domain.useCase.MakeFavoriteItemUseCase
import com.example.shop.presentation.viewModel.ItemViewModel

class ItemViewModelFactory(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val insertItemUseCase: InsertItemUseCase,
    private val makeFavoriteItemUseCase: MakeFavoriteItemUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            return ItemViewModel(getAllItemsUseCase, insertItemUseCase, makeFavoriteItemUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
