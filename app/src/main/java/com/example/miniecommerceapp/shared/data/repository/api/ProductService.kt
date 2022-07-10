package com.example.miniecommerceapp.shared.data.repository.api

import com.example.miniecommerceapp.productDetails.data.ProductDetailsEntity
import com.example.miniecommerceapp.productList.data.ProductEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {

    @GET("products")
    suspend fun getProductList(): List<ProductEntity>

    @GET("productDetails")
    suspend fun getProductDetails(@Query("productId") productId: String): ProductDetailsEntity
}