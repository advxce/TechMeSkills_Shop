package com.example.shop.presentation.diffUtil

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.shop.presentation.entity.ItemUi

class ItemDiffUtil(
    private val oldList: List<ItemUi>,
    private val newList: List<ItemUi>,
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
        val areItemsTheSame = oldList[oldItemPosition].id == newList[newItemPosition].id
        Log.i("Util", "areItemsTheSame: "+ areItemsTheSame.toString())
        return  areItemsTheSame
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val areContentTheSame = oldList[oldItemPosition] == newList[newItemPosition]
        Log.i("Util", "areContentsTheSame: "+ areContentTheSame.toString())
        return areContentTheSame
    }
}
