package com.example.shop.presentation.loadItemScreen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.presentation.loadItemScreen.viewModel.LoadItemsViewModel

class LoadItemFactory(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadItemsViewModel::class.java)) {
            return LoadItemsViewModel(getAllItemsUseCase, deleteItemUseCase) as T
        }
        throw IllegalArgumentException("Cannot find viewModel")
    }
}
