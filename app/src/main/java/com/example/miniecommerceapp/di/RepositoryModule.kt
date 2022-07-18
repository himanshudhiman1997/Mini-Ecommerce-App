package com.example.miniecommerceapp.di

import android.content.Context
import androidx.room.Room
import com.example.miniecommerceapp.shared.data.repository.api.ApiClient
import com.example.miniecommerceapp.shared.data.repository.ProductRepository
import com.example.miniecommerceapp.shared.data.repository.api.ProductRepositoryAPI
import com.example.miniecommerceapp.shared.data.repository.api.ProductService
import com.example.miniecommerceapp.shared.wishlist.data.repository.database.WishlistDatabaseRepository
import com.example.miniecommerceapp.shared.wishlist.data.repository.WishlistRepository
import com.example.miniecommerceapp.shared.wishlist.data.repository.database.AppDatabase
import com.example.miniecommerceapp.shared.wishlist.data.repository.database.WishlistDAO
import com.example.miniecommerceapp.shared.wishlist.data.repository.sharedprefs.WishlistSharedPrefRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

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

    /*@Provides
    fun providesWishlistRepository(wishlistDatabaseRepository: WishlistDatabaseRepository): WishlistRepository =
        wishlistDatabaseRepository*/

    @Provides
    fun providesWishlistRepository(wishlistSharedPrefRepo: WishlistSharedPrefRepo): WishlistRepository =
        wishlistSharedPrefRepo

    @Provides
    fun providesWishlistDatabaseRepository(wishlistDAO: WishlistDAO): WishlistDatabaseRepository {
        return WishlistDatabaseRepository(wishlistDAO)
    }

    @Provides
    fun providesWishlistDAO(@ApplicationContext context: Context): WishlistDAO {
        val db =
            Room.databaseBuilder(context, AppDatabase::class.java, "ecommerce_database").build()
        return db.wishlistDao()
    }

    @Provides
    fun providesWishlistSharedPrefRepo(@ApplicationContext context: Context): WishlistSharedPrefRepo {
        return WishlistSharedPrefRepo(context)
    }
}