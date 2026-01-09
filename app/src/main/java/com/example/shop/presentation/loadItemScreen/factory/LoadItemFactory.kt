package com.example.shop.presentation.loadItemScreen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shop.domain.useCase.BookmarkItemUseCase
import com.example.shop.domain.useCase.DeleteItemUseCase
import com.example.shop.domain.useCase.FindItemUseCase
import com.example.shop.domain.useCase.GetAllItemsUseCase
import com.example.shop.domain.useCase.GetItemByIdUseCase
import com.example.shop.presentation.loadItemScreen.viewModel.LoadItemsViewModel

class LoadItemFactory(
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val findItemUseCase: FindItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val bookmarkItemUseCase: BookmarkItemUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoadItemsViewModel::class.java)) {
            return LoadItemsViewModel(
                getAllItemsUseCase = getAllItemsUseCase,
                findItemUseCase = findItemUseCase,
                deleteItemUseCase = deleteItemUseCase,
                bookmarkItemUseCase = bookmarkItemUseCase
            ) as T
        }
        throw IllegalArgumentException("Cannot find viewModel")
    }
}
