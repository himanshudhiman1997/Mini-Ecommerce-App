package com.example.miniecommerceapp.shared.wishlist.business

import com.example.miniecommerceapp.shared.wishlist.data.repository.WishlistRepository
import javax.inject.Inject

class AddOrRemoveFromWishlistUseCase @Inject constructor(
    private val isProductInWishlistUseCase: IsProductInWishlistUseCase,
    private val wishlistRepository: WishlistRepository
) {
    suspend fun execute(productId: String) {
        if (isProductInWishlistUseCase.execute(productId)) {
            wishlistRepository.removeFromWishlist(productId)
        } else {
            wishlistRepository.addToWishlist(productId)
        }
    }
}