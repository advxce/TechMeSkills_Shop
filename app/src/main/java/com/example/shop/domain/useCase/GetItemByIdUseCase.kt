package com.example.shop.domain.useCase

import com.example.shop.domain.entity.ItemDomain
import com.example.shop.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetItemByIdUseCase {
    suspend operator fun invoke(id:Long): ItemDomain
}
class GetItemByIdUseCaseImpl @Inject constructor(
    private val itemRepository: ItemRepository
): GetItemByIdUseCase{
    override suspend fun invoke(id:Long): ItemDomain = withContext(Dispatchers.IO) {
        return@withContext itemRepository.getItemById(id)
    }

}