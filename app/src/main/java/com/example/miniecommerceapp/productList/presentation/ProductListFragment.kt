package com.example.miniecommerceapp.productList.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniecommerceapp.databinding.ProductListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private lateinit var binding: ProductListFragmentBinding
    private val adapter = ProductCardListAdapter(::onItemClicked, ::onFavoriteItemClicked)

    private fun onItemClicked(productCardViewState: ProductCardViewState) {
        findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToProductDetailsFragment())
    }

    private fun onFavoriteItemClicked(viewState: ProductCardViewState) {
        viewModel.favoriteIconClicked(viewState.id)
    }

    private val viewModel: ProductListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductListFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewProductList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.viewProductList.adapter = adapter

        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            updateUI(binding, viewState)
        }

        viewModel.loadProductList()
    }

    private fun updateUI(binding: ProductListFragmentBinding, viewState: ProductListViewState) {
        when (viewState) {
            is ProductListViewState.Content -> {
                binding.viewProductList.isVisible = true
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = false
                adapter.setData(viewState.productList)
            }
            is ProductListViewState.Error -> {
                binding.errorMessage.text = viewState.errorMsg
                binding.viewProductList.isVisible = false
                binding.errorView.isVisible = true
                binding.loadingView.isVisible = false
            }
            ProductListViewState.Loading -> {
                binding.viewProductList.isVisible = false
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = true
            }
        }
    }
}