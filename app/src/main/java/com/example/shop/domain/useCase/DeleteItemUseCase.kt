package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository

interface DeleteItemUseCase{
    fun invoke(item: ItemDomain)
}

class DeleteItemUseCaseImpl(
    private val repository: ItemRepository
): DeleteItemUseCase {
    override fun invoke(item: ItemDomain) {
        repository.deleteItem(item)
    }
}