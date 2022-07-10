package com.example.miniecommerceapp.di

import com.example.miniecommerceapp.ApiClient
import com.example.miniecommerceapp.ProductRepository
import com.example.miniecommerceapp.ProductRepositoryAPI
import com.example.miniecommerceapp.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providesProductService(): ProductService {
        return ApiClient.getService()
    }

    @Provides
    fun providesProductRepositoryAPI(service: ProductService): ProductRepositoryAPI =
        ProductRepositoryAPI(service)

    @Provides
    fun providesProductRepository(productRepositoryAPI: ProductRepositoryAPI): ProductRepository =
        productRepositoryAPI

}