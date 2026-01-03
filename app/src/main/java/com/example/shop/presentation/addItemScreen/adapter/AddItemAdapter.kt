package com.example.shop.presentation.addItemScreen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.R
import com.example.shop.databinding.SampleItemShopBinding
import com.example.shop.domain.entity.ItemState
import com.example.shop.presentation.diffUtil.ItemDiffUtil
import com.example.shop.presentation.entity.ItemUi

class AddItemAdapter(
    private val context: Context,
    private val onCancel: (Long) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList: MutableList<ItemUi> = mutableListOf()

    fun submitList(newList: List<ItemUi>) {
        val diffUtil = DiffUtil.calculateDiff(ItemDiffUtil(itemList, newList))
        itemList.clear()
        itemList.addAll(newList)
        diffUtil.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val items =
            SampleItemShopBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ItemViewHolder(items)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as ItemViewHolder).bind(itemList[position], context)
    }

    override fun getItemCount(): Int = itemList.size

    inner class ItemViewHolder(private val binding: SampleItemShopBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemUi, context: Context) = with(binding) {

            chkBox.visibility = View.GONE
            itemTitle.text = item.title

            itemState.text = when (item.itemState) {
                ItemState.ERROR -> context.getString(R.string.itemStateError)
                ItemState.LOADING -> context.getString(R.string.itemStateLoading)
                ItemState.SUCCESS -> context.getString(R.string.itemStateSuccess)
                ItemState.CANCELED -> context.getString(R.string.itemStateCancel)
            }
            itemAppBtn.text = context.getString(R.string.cancel_btn_txt)
            itemAppBtn.isEnabled = item.itemState == ItemState.LOADING
            itemAppBtn.setOnClickListener {
                onCancel(item.id)
            }
        }
    }
}
