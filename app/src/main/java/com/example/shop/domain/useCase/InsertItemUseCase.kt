package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository

interface InsertItemUseCase{
    fun invoke(item: ItemDomain)
}

class InsertItemUseCaseImpl(
    private val repository: ItemRepository
): InsertItemUseCase {
    override fun invoke(item: ItemDomain) {
        repository.insertItem(item)
    }
}