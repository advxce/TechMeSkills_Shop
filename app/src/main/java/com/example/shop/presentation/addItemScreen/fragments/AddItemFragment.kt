package com.example.shop.presentation.addItemScreen.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop.R
import com.example.shop.ShopApplication
import com.example.shop.databinding.FragmentAddItemBinding
import com.example.shop.presentation.addItemScreen.adapter.AddItemAdapter
import com.example.shop.presentation.addItemScreen.factory.AddItemViewModelFactory
import com.example.shop.presentation.addItemScreen.viewModel.AddItemViewModel
import com.example.shop.presentation.loadItemScreen.fragments.LoadItemsFragment
import javax.inject.Inject

class AddItemFragment : Fragment() {

    private var adapter: AddItemAdapter? = null
    private var binding: FragmentAddItemBinding? = null

    @Inject
    lateinit var factory: AddItemViewModelFactory

    private val addItemViewModel: AddItemViewModel by viewModels{
        factory
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as ShopApplication).component.inject(this)
        super.onAttach(context)
    }

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

        setupObservers()
        setupAdapter()
        setupButtons()
    }


    private fun setupButtons() {
        withBinding {
            addButton.root.text = getString(R.string.add_item)
            addButton.root.backgroundTintList =
                resources.getColorStateList(R.color.appAddButtonColor, null)
            addButton.root.setOnClickListener {
                addItemViewModel.insertItem(
                    editTxtEnterTitle.root.text.toString(),
                    editTxtEnterDescription.root.text.toString(),
                    R.drawable.java_course
                )
                editTxtEnterTitle.root.setText("")
                editTxtEnterDescription.root.setText("")
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
                    addItemViewModel.onCancel(it)
                },
            )

            recView.adapter = adapter
            recView.layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun setupObservers() {
        addItemViewModel.itemList.observe(viewLifecycleOwner) {
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
