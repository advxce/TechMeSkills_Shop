package com.example.shop.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.databinding.ItemShopBinding
import com.example.shop.domain.entity.ItemState
import com.example.shop.presentation.diffUtil.ItemDiffUtil
import com.example.shop.presentation.entity.ItemUi

class ItemAdapter(
    private val setCheck: (ItemUi) -> Unit,
    private val onDelete:(ItemUi)-> Unit,
    private val onCancel:(Long)-> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<ItemUi> = mutableListOf()

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
        val diffResult = DiffUtil.calculateDiff(ItemDiffUtil(list, newList))
        list = newList.toMutableList()
        diffResult.dispatchUpdatesTo(this)
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

            binding.deleteBtn.text = "Delete"
            binding.deleteBtn.setOnClickListener {
                onDelete(item)
            }
            binding.cancelBtn.isEnabled = item.itemState == ItemState.LOADING
            binding.cancelBtn.setOnClickListener {
                onCancel(item.id)
            }
            binding.itemStateTxt.text = when(item.itemState){
                ItemState.ERROR -> "error"
                ItemState.LOADING -> "loading"
                ItemState.SUCCESS -> "success"
                ItemState.CANCELED -> "cancel"
            }
        }
    }
}
