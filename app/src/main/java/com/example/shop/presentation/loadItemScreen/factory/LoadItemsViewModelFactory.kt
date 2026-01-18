package com.example.shop.presentation.loadItemScreen.factory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.FindItemUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.UpdateItemUseCase
import com.example.shop.presentation.loadItemScreen.viewModel.LoadItemsViewModel
import javax.inject.Inject

class LoadItemsViewModelFactory @Inject constructor(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val findItemUseCase: FindItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadItemsViewModel::class.java)) {
            return LoadItemsViewModel(
                getAllItemsUseCase,
                findItemUseCase,
                deleteItemUseCase,
                updateItemUseCase,
                savedStateHandle
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}