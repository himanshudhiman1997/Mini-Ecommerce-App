package com.example.miniecommerceapp.productDetails.presentation

import com.example.miniecommerceapp.productDetails.business.ProductDetails


sealed class ProductDetailsViewState {
    object Loading : ProductDetailsViewState()
    data class Content(val product: ProductDetails) : ProductDetailsViewState()
    object Error : ProductDetailsViewState()
}