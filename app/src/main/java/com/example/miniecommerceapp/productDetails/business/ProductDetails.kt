package com.example.miniecommerceapp.productDetails.business

data class ProductDetails(
    val title: String,
    val description: String,
    val fullDescription: String,
    val price: String,
    val imageUrl: String,
    val pros: List<String>,
    val cons: List<String>
)
