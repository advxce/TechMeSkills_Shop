package com.example.shop.presentation.loadItemScreen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.useCase.FindItemUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.GetItemByIdUseCase
import com.example.shop.presentation.loadItemScreen.viewModel.LoadItemsViewModel

class LoadItemFactory(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val findItemUseCase: FindItemUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadItemsViewModel::class.java)) {
            return LoadItemsViewModel( getAllItemsUseCase = getAllItemsUseCase, findItemUseCase =  findItemUseCase, getItemByIdUseCase = getItemByIdUseCase) as T
        }
        throw IllegalArgumentException("Cannot find viewModel")
    }
}
