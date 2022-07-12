package com.example.miniecommerceapp.shared.wishlist.data.repository

import com.example.miniecommerceapp.shared.wishlist.data.repository.database.FavoriteProductEntity
import com.example.miniecommerceapp.shared.wishlist.data.repository.database.WishlistDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WishlistDatabaseRepository @Inject constructor(private val wishlistDAO: WishlistDAO) :
    WishlistRepository {

    override suspend fun isFavorite(productId: String): Boolean {
        return withContext(Dispatchers.IO) {
            wishlistDAO.isProductFavorite(productId) != null
        }
    }

    override suspend fun addToWishlist(productId: String) {
        return withContext(Dispatchers.IO) {
            wishlistDAO.addProductToFavorites(FavoriteProductEntity(productId, ""))
        }
    }

    override suspend fun removeFromWishlist(productId: String) {
        return withContext(Dispatchers.IO) {
            wishlistDAO.removeProductFromFavorites(FavoriteProductEntity(productId, ""))
        }
    }
}