package com.example.miniecommerceapp.productDetails.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.miniecommerceapp.databinding.ProductDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private lateinit var binding: ProductDetailsFragmentBinding
    private val productDetailsViewModel: ProductDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductDetailsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productDetailsViewModel.viewState.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        //get data
        productDetailsViewModel.loadProduct("someProductId")

    }

    private fun updateUI(viewState: ProductDetailsViewState?) {
        when (viewState) {
            is ProductDetailsViewState.Loading -> {
                binding.loadingView.isVisible = true
            }
            is ProductDetailsViewState.Content -> {
                with(binding) {
                    loadingView.isVisible = false

                    val product = viewState.product
                    viewProductTitle.text = product.title
                    viewFullDescription.text = product.fullDescription
                    viewPrice.text = product.price

                    Glide.with(this@ProductDetailsFragment).load(product.imageUrl)
                        .into(viewProductImage)
                }
            }
            ProductDetailsViewState.Error -> {
                binding.loadingView.isVisible = false
            }
            null -> TODO()
        }
    }
}