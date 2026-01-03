package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository

interface GetItemByIdUseCase {
    suspend operator fun invoke(id:Long): ItemDomain?
}
class GetItemByIdUseCaseImpl(
    private val itemRepository: ItemRepository
): GetItemByIdUseCase{
    override suspend fun invoke(id:Long): ItemDomain? {
        return itemRepository.getItemById(id)
    }

}