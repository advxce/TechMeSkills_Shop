package com.example.shop.presentation.addItemScreen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop.R
import com.example.shop.databinding.FragmentAddItemBinding
import com.example.shop.di.AppItem
import com.example.shop.di.ItemModule
import com.example.shop.presentation.addItemScreen.adapter.AddItemAdapter
import com.example.shop.presentation.addItemScreen.factory.AddViewModelFactory
import com.example.shop.presentation.addItemScreen.viewModel.AddItemViewModel
import com.example.shop.presentation.loadItemScreen.fragments.LoadItemsFragment

class AddItemFragment : Fragment() {

    private var adapter: AddItemAdapter? = null
    private var binding: FragmentAddItemBinding? = null

    private val di: ItemModule by lazy {
        ItemModule()
    }

    private val addItemFactory: AddViewModelFactory by lazy {
        AddViewModelFactory(di.insertItemUseCase)
    }

    private var addItemViewModel: AddItemViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupObservers()
        setupAdapter()
        setupButtons()
    }

    private fun setupViewModel() {
        addItemViewModel =
            ViewModelProvider(requireActivity(), addItemFactory)[AddItemViewModel::class.java]
    }

    private fun setupButtons() {
        withBinding {
            addButton.root.text = getString(R.string.add_item)
            addButton.root.backgroundTintList =
                resources.getColorStateList(R.color.appAddButtonColor, null)
            addButton.root.setOnClickListener {
                addItemViewModel?.insertItem(
                    editTxtEnterTitle.root.text.toString(),
                    R.drawable.java_course
                )
                editTxtEnterTitle.root.setText("")
            }
            backBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragContainerView, LoadItemsFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun setupAdapter() {
        withBinding {
            adapter = AddItemAdapter(
                requireActivity(),
                onCancel = {
                    addItemViewModel?.onCancel(it)
                },
            )

            recView.adapter = adapter
            recView.layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun setupObservers() {
        addItemViewModel?.itemList?.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun withBinding(block: FragmentAddItemBinding.() -> Unit) {
        binding?.let {
            block(it)
        }
    }
}
