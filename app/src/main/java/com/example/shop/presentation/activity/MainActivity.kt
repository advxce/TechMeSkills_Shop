package com.example.shop.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop.R
import com.example.shop.databinding.ActivityMainBinding
import com.example.shop.di.ItemModule
import com.example.shop.presentation.adapter.ItemAdapter
import com.example.shop.presentation.entity.ItemUi
import com.example.shop.presentation.factory.ItemViewModelFactory
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
            itemModule.makeFavoriteItemUseCase
        )
    }

    private var itemViewModel: ItemViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViewModel()

        setupAdapter()
        setupObservers()

        setupButton()
    }

    private fun setupViewModel() {
        itemViewModel = ViewModelProvider(this, itemFactory)[ItemViewModel::class.java]
    }

    private fun setupObservers() {
        itemViewModel?.getItems()

        itemViewModel?.itemList?.observe(this) { list ->
            adapter?.submitList(list)
            Log.i("ITEM1", list.toString())
        }
    }
    private fun setupAdapter() {
        adapter = ItemAdapter(emptyList<ItemUi>()) {
            itemViewModel?.changeMarkItem(it, !it.marked)
        }

        withBinding {
            recView.adapter = adapter
            recView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupButton() {
        withBinding {
            addBtn.setOnClickListener {
                itemViewModel?.insertItem(
                    ItemUi(
                        id = itemViewModel?.getItems()?.size?.plus(1) ?: 0,
                        title = editTitle.text.toString(),
                        marked = false
                    )
                )
                editTitle.setText("")
                adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun withBinding(block: ActivityMainBinding.() -> Unit) {
        binding?.let {
            block(it)
        }
    }
}
