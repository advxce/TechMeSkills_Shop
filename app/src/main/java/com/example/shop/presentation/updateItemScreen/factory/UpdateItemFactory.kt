package com.example.shop.presentation.updateItemScreen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.useCase.GetItemByIdUseCase
import com.example.shop.domain.useCase.UpdateItemUseCase
import com.example.shop.presentation.updateItemScreen.viewModels.UpdateItemViewModel

class UpdateItemFactory(
    private val updateItemUseCase: UpdateItemUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateItemViewModel::class.java))
            return UpdateItemViewModel(updateItemUseCase) as T
        throw IllegalArgumentException("")
    }
}