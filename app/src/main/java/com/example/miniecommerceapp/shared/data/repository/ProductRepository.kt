package com.example.miniecommerceapp.shared.data.repository

import com.example.miniecommerceapp.productDetails.business.ProductDetails
import com.example.miniecommerceapp.productList.business.Product

interface ProductRepository {

    suspend fun getProductList(): List<Product>

    suspend fun getProductDetails(productId: String) : ProductDetails
}