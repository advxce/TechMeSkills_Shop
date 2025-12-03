package com.example.shop.presentation.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import java.time.LocalDateTime
import java.util.Date

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
            }
        )

        setupViewModel()

        setupAdapter()
        setupObservers()
        setupEditText()
        setupButton()
        itemViewModel?.getItems()



    }

    private fun setupViewModel() {
        itemViewModel = ViewModelProvider(this, itemFactory)[ItemViewModel::class.java]
    }

    private fun setupObservers() {

        itemViewModel?.itemList?.observe(this) { list ->
            adapter?.submitList(list)
            Log.i("ITEM1", list.toString())
        }
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
                        id = System.currentTimeMillis(),
                        title = editTitle.root.text.toString(),
                        marked = false
                    )
                )
                editTitle.root.setText("")
            }
        }
    }

    private fun withBinding(block: ActivityMainBinding.() -> Unit) {
        binding?.let {
            block(it)
        }
    }
}
