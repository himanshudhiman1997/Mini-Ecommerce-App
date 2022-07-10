package com.example.miniecommerceapp

interface ProductRepository {
    suspend fun getProductList(): List<ProductCardViewState>
}