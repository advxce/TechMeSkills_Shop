package com.example.shop.presentation.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.shop.presentation.entity.ItemUi

class ItemDiffUtil(
    private val newList: List<ItemUi>,
    private val oldList: List<ItemUi>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldItemPosition == newItemPosition
    }
}
