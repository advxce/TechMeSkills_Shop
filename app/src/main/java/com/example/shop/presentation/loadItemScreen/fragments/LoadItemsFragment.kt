package com.example.shop.presentation.loadItemScreen.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop.R
import com.example.shop.databinding.FragmentLoadItemsBinding
import com.example.shop.di.ItemModule
import com.example.shop.presentation.addItemScreen.fragments.AddItemFragment
import com.example.shop.presentation.entity.ItemStateUi
import com.example.shop.presentation.entity.ItemUi
import com.example.shop.presentation.loadItemScreen.adapter.LoadItemAdapter
import com.example.shop.presentation.loadItemScreen.factory.LoadItemFactory
import com.example.shop.presentation.loadItemScreen.viewModel.LoadItemsViewModel
import com.example.shop.presentation.textWatcher.SimpleTextWatcher
import kotlinx.coroutines.launch

class LoadItemsFragment : Fragment() {

    private var binding: FragmentLoadItemsBinding? = null
    private var adapter: LoadItemAdapter? = null

    private val di: ItemModule by lazy {
        ItemModule()
    }

    private val loadItemFactory: LoadItemFactory by lazy {
        LoadItemFactory(
            di.getItemUseCase,
            di.findItemUseCase,
            di.getItemByIdUseCase
        )
    }
    private var loadItemViewModel: LoadItemsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadItemsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupButtons()
        setupEditText()
        setupAdapter()
        setupObserver()



    }

    private fun setupViewModel() {
        loadItemViewModel =
            ViewModelProvider(requireActivity(), loadItemFactory)[LoadItemsViewModel::class.java]
    }

    private fun setupButtons() {
        withBinding {
            addItemBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragContainerView, AddItemFragment())
                    .addToBackStack(null)
                    .commit()
            }

            loadBtn.root.text = getString(R.string.load_items)
            loadBtn.root.setOnClickListener {
                loadItemViewModel?.loadItems()
                editTxtEnterTitle.root.isEnabled = true

            }
        }
    }

    private fun setupAdapter() {
        adapter = LoadItemAdapter(
            requireActivity(),
            onDelete = {

            },
            setCheck = {

            },
            onSelect = {
                loadItemViewModel?.getItemById(it.id)
            }
        )
        withBinding {
            recView.adapter = adapter
            recView.layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun setupEditText() {
        withBinding {
            editTxtEnterTitle.root.isEnabled = false
            editTxtEnterTitle.root.addTextChangedListener(object : SimpleTextWatcher() {
                override fun afterTextChanged(p0: Editable?) {
                    loadItemViewModel?.onSearchQuery(p0.toString())
                }
            })

        }
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            loadItemViewModel?.itemListState?.collect { state ->
                when (state) {
                    is ItemStateUi.Cancelled -> {
                        showInfoText(state.message)
                    }

                    is ItemStateUi.Error -> {
                        showInfoText(state.message)
                    }

                    ItemStateUi.Loading -> {
                        showProgressBar()
                    }

                    is ItemStateUi.Success -> {
                        showRecView()
                        showItems(state.list)
                    }
                }
            }
        }

        loadItemViewModel?.observeSearchFlow()
    }

    private fun showItems(list: List<ItemUi>) {
        adapter?.submitList(list)
    }

    private fun showProgressBar() {
        withBinding {
            recView.visibility = View.GONE
            infoTextView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun showRecView() {
        withBinding {
            recView.visibility = View.VISIBLE
            infoTextView.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }

    private fun showInfoText(message: String) {
        withBinding {
            recView.visibility = View.GONE
            infoTextView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE

            infoTextView.text = message
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun withBinding(block: FragmentLoadItemsBinding.() -> Unit) {
        binding?.let {
            block(it)
        }
    }
}
