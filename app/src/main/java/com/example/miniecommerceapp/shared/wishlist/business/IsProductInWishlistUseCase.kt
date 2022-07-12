package com.example.miniecommerceapp.shared.wishlist.business

import com.example.miniecommerceapp.shared.wishlist.data.repository.WishlistRepository
import javax.inject.Inject

class IsProductInWishlistUseCase @Inject constructor(private val wishlistRepository: WishlistRepository) {

    suspend fun execute(productId: String): Boolean = wishlistRepository.isFavorite(productId)
}