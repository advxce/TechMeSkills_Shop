package com.example.shop.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop.R
import com.example.shop.databinding.ActivityMainBinding
import com.example.shop.di.ItemModule
import com.example.shop.domain.entity.ItemState
import com.example.shop.presentation.adapter.ItemAdapter
import com.example.shop.presentation.entity.ItemUi
import com.example.shop.presentation.factory.ItemViewModelFactory
import com.example.shop.presentation.state.ItemShopState
import com.example.shop.presentation.viewModel.ItemViewModel

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private var adapter: ItemAdapter? = null

    private val itemModule by lazy {
        ItemModule(applicationContext)
    }

    private val itemFactory by lazy {
        ItemViewModelFactory(
            itemModule.getItemUseCase,
            itemModule.insertItemUseCase,
            itemModule.makeFavoriteItemUseCase,
            itemModule.deleteItemUseCase
        )
    }

    private var itemViewModel: ItemViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val parent = binding?.root
        if (parent != null) {
            ViewCompat.setOnApplyWindowInsetsListener(parent) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(
                    systemBars.left + v.paddingLeft,
                    systemBars.top + v.paddingTop,
                    systemBars.right + v.paddingRight,
                    systemBars.bottom + v.paddingBottom
                )
                insets
            }
        }

        adapter = ItemAdapter(
            setCheck = {
                itemViewModel?.changeMarkItem(it, !it.marked)
            },
            onDelete = {
                itemViewModel?.deleteItem(it)
            },
            onCancel = {

            }
        )

        setupViewModel()

        setupAdapter()
        getAllAddItems()
        setupEditText()
        setupButton()
    }

    private fun setupViewModel() {
        itemViewModel = ViewModelProvider(this, itemFactory)[ItemViewModel::class.java]
    }

    private fun setupAdapter() {
        withBinding {
            recView.adapter = adapter
            recView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupEditText() {
        withBinding {
            editTitle.root.hint = "enter title"
        }
    }


    private fun setupButton() {
        withBinding {
            addBtn.appBtn.text = ContextCompat.getString(this@MainActivity, R.string.add_button)
            addBtn.appBtn.setOnClickListener {
                itemViewModel?.insertItem(
                    ItemUi(
                        title = editTitle.root.text.toString(),
                        itemState = ItemState.LOADING
                    )
                )
                editTitle.root.setText("")
            }
            loadBtn.root.text = "Load data"
            loadBtn.appBtn.setOnClickListener {
                setupUiState()
                itemViewModel?.getItemsWithState()
            }
        }
    }

    private fun setupUiState() {
        itemViewModel?.itemListState?.observe(this) { itemState ->

            when (itemState) {
                is ItemShopState.Success -> {
                    hideProgressBar()
                    val allItems = (itemState.itemList + (itemViewModel?.itemList?.value ?: emptyList()))
                    showItems(allItems)
                }

                is ItemShopState.Loading -> {
                    showProgressBar()
                }

                is ItemShopState.Error -> {
                    showErrorText(itemState.msg)
                }

                is ItemShopState.Empty -> {
                    showEmptyText()
                }
            }
        }
    }

    private fun getAllAddItems() {
        itemViewModel?.itemList?.observe(this) {itemList->
            val allItems = (((itemViewModel?.itemListState?.value as ItemShopState.Success).itemList) + itemList)
            adapter?.submitList(allItems)
            Log.i("Tag", "$allItems" )
        }
    }

    private fun showItems(items: List<ItemUi>) {
        adapter?.submitList(items)
    }

    private fun hideProgressBar() {
        withBinding {
            recView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            textInfo.visibility = View.GONE
        }
    }

    private fun showProgressBar() {
        withBinding {
            progressBar.visibility = View.VISIBLE
            recView.visibility = View.GONE
            textInfo.visibility = View.VISIBLE
            textInfo.text = "Loading..."
        }
    }

    private fun showErrorText(msg: String) {
        withBinding {
            progressBar.visibility = View.GONE
            textInfo.visibility = View.VISIBLE
            textInfo.text = msg
            recView.visibility = View.GONE
        }
    }

    private fun showEmptyText() {
        withBinding {
            textInfo.visibility = View.VISIBLE
            recView.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }

    private fun withBinding(block: ActivityMainBinding.() -> Unit) {
        binding?.let {
            block(it)
        }
    }

    override fun onStop() {

        super.onStop()
        itemViewModel?.cancelLoading()
    }

}
