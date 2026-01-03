package com.example.shop.presentation.addItemScreen.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.useCase.InsertItemUseCase
import com.example.shop.presentation.addItemScreen.viewModel.AddItemViewModel

class AddViewModelFactory(
    private val context: Context,
    private val insertItemUseCase: InsertItemUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddItemViewModel::class.java)) {
            return AddItemViewModel(context, insertItemUseCase) as T
        }
        throw IllegalArgumentException("unknown viewModel")
    }
}
