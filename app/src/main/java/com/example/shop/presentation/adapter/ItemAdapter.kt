package com.example.shop.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.databinding.ItemShopBinding
import com.example.shop.presentation.diffUtil.ItemDiffUtil
import com.example.shop.presentation.entity.ItemUi

class ItemAdapter(
    private var list: List<ItemUi>,
    private val setCheck: (item: ItemUi) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val item = ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(item)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as ItemViewHolder).bind(list[position])
    }

    fun submitList(newList: List<ItemUi>) {
        val calc = DiffUtil.calculateDiff(ItemDiffUtil(list, newList))
        list = newList
        calc.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = list.size

    inner class ItemViewHolder(private val binding: ItemShopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemUi) {
            binding.idText.text = item.id.toString()
            binding.titleText.text = item.title.toString()
            binding.checkBox.setOnClickListener {
                setCheck(item)
            }
            binding.checkBox.isChecked = item.marked
        }
    }
}
