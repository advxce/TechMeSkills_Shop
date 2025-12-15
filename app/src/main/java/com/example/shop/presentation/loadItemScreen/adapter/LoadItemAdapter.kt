package com.example.shop.presentation.loadItemScreen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.R
import com.example.shop.databinding.SampleItemShopBinding
import com.example.shop.presentation.diffUtil.ItemDiffUtil
import com.example.shop.presentation.entity.ItemUi

class LoadItemAdapter(
    private val context: Context,
    private val onDelete: (ItemUi) -> Unit,
    private val setCheck: (ItemUi) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList: MutableList<ItemUi> = mutableListOf()

    fun submitList(newList: List<ItemUi>){
        val diffUtil = DiffUtil.calculateDiff(ItemDiffUtil(itemList, newList))
        itemList.clear()
        itemList.addAll(newList)
        diffUtil.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val item = SampleItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(item)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as ItemViewHolder).bind(itemList[position], context)
    }

    override fun getItemCount(): Int = itemList.size

    inner class ItemViewHolder(private val binding: SampleItemShopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemUi, context: Context) = with(binding) {
            chkBox.setOnClickListener {
                setCheck(item)
            }
            chkBox.isChecked = item.marked
            itemTitle.text = item.title
            itemState.visibility = View.GONE
            itemAppBtn.setOnClickListener {
                onDelete(item)
            }
            itemAppBtn.text = context.getString(R.string.delete_btn_txt)
        }
    }
}
