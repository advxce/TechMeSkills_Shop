package com.example.shop.presentation.addItemScreen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.InsertItemUseCase
import com.example.shop.presentation.addItemScreen.viewModel.AddItemViewModel
import javax.inject.Inject

class AddItemViewModelFactory @Inject constructor(
    private val insertItemUseCase: InsertItemUseCase,
    private val getAllItemsUseCase: GetAllItemsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddItemViewModel::class.java)) {
            return AddItemViewModel(
                insertItemUseCase,
                getAllItemsUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}