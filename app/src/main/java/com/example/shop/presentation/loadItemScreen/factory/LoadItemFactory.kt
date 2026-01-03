package com.example.shop.presentation.loadItemScreen.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.FindItemUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.MakeFavoriteItemUseCase
import com.example.shop.presentation.loadItemScreen.viewModel.LoadItemsViewModel

class LoadItemFactory(
    private val context: Context,
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val findItemUseCase: FindItemUseCase,
    private val makeFavoriteItemUseCase: MakeFavoriteItemUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadItemsViewModel::class.java)) {
            return LoadItemsViewModel(context, getAllItemsUseCase, deleteItemUseCase, findItemUseCase, makeFavoriteItemUseCase) as T
        }
        throw IllegalArgumentException("Cannot find viewModel")
    }
}
