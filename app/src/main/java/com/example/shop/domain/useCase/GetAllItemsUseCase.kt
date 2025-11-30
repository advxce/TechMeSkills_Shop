package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository

interface GetAllItemsUseCase {
    fun invoke(): List<ItemDomain>
}

class GetAllItemsUseCaseImpl(
    private val repository: ItemRepository
) : GetAllItemsUseCase {
    override fun invoke(): List<ItemDomain> {
        return repository.getAllItems()
    }
}
