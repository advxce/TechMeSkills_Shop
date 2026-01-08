package com.example.shop.presentation.updateItemScreen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.shop.databinding.FragmentUpdateItemBinding

import com.example.shop.presentation.entity.ItemStateUi
import com.example.shop.presentation.updateItemScreen.factory.UpdateItemFactory
import com.example.shop.presentation.updateItemScreen.viewModels.UpdateItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateItemFragment : Fragment() {

    private var binding: FragmentUpdateItemBinding? = null


    private var itemId: Long? = null
    val newItemId: Long by lazy {
        itemId ?: throw IllegalArgumentException("Don`t have arguments")
    }
    private val updateItemViewModel: UpdateItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateItemBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemId = arguments?.getLong(ITEM_KEY)

        setupViewModel()
        setupObservers()
        setupEditTexts()
        setupButtons()
        setupImageView()
    }

    private fun setupButtons() {
        withBinding {
            updateItemBtn.root.text = "Update Item"
            updateItemBtn.root.setOnClickListener {
                updateItemViewModel?.updateItem(
                    itemId ?: throw IllegalArgumentException("Don`t have arguments"),
                    title = itemTitle.root.text.toString(),
                    description = itemDescription.root.text.toString(),
                    image = "" +
                        ""
                )
                itemTitle.root.setText("")
                itemDescription.root.setText("")
            }
        }
    }

    private fun setupViewModel() {
        updateItemViewModel?.loadItem(
            newItemId
        )
    }

    private fun setupEditTexts() {
        withBinding {
            itemTitle.root.hint = "enter title"
            itemTitle.root.setText(
                updateItemViewModel?.getItem(
                    newItemId
                )?.title
            )
            itemDescription.root.hint = "enter description"
            itemDescription.root.setText(
                updateItemViewModel?.getItem(
                    newItemId
                )?.description
            )
        }
    }

    private fun setupImageView(){
        withBinding {
            val image = updateItemViewModel?.getItem(newItemId)
            val imageState = image?.image ?: ""
            itemView.load(if(imageState.isNotEmpty()) image?.image else image?.localImage )
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            updateItemViewModel?.itemState?.collect { state ->
                when (state) {
                    is ItemStateUi.Cancelled -> {}
                    is ItemStateUi.Error -> {}
                    ItemStateUi.Loading -> {
                        showProgressBar()
                    }

                    is ItemStateUi.Success -> {
                        showItem()
                    }
                }
            }
        }
    }

    private fun showItem() {
        withBinding {
            progressBar.visibility = View.GONE
            itemTitle.root.visibility = View.VISIBLE
            itemDescription.root.visibility = View.VISIBLE
            itemDescription.root.visibility = View.VISIBLE
            updateItemBtn.root.visibility = View.VISIBLE
        }
        Toast.makeText(requireActivity(), "Item successful updated", Toast.LENGTH_SHORT)
            .show()
    }

    private fun showProgressBar() {
        withBinding {
            progressBar.visibility = View.VISIBLE
            itemTitle.root.visibility = View.GONE
            itemDescription.root.visibility = View.GONE
            itemDescription.root.visibility = View.GONE
            updateItemBtn.root.visibility = View.GONE
        }
    }

    private fun withBinding(block: FragmentUpdateItemBinding.() -> Unit) {
        binding?.let {
            block.invoke(it)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        const val ITEM_KEY = "itemId"

        fun newInstance(id: Long) = UpdateItemFragment()
            .apply {
                arguments = Bundle()
                    .apply {
                        putLong(ITEM_KEY, id)
                    }
            }
    }
}
