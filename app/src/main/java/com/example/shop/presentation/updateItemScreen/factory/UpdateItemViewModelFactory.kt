package com.example.shop.presentation.updateItemScreen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.useCase.GetItemByIdUseCase
import com.example.shop.domain.useCase.UpdateItemUseCase
import com.example.shop.presentation.loadItemScreen.viewModel.LoadItemsViewModel
import com.example.shop.presentation.updateItemScreen.viewModels.UpdateItemViewModel
import javax.inject.Inject

class UpdateItemViewModelFactory @Inject constructor(
    private val updateItemUseCase: UpdateItemUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateItemViewModel::class.java)) {
            return UpdateItemViewModel(
                updateItemUseCase,
                getItemByIdUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}