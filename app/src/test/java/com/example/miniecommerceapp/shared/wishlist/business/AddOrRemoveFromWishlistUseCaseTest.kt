package com.example.miniecommerceapp.shared.wishlist.business

import com.example.miniecommerceapp.shared.wishlist.data.repository.WishlistRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
internal class AddOrRemoveFromWishlistUseCaseTest {
    private val isProductInWishlistUseCase = mockk<IsProductInWishlistUseCase>()
    private val wishlistRepository = mockk<WishlistRepository>(relaxed = true)
    private lateinit var useCase : AddOrRemoveFromWishlistUseCase

    @Before
    fun setup() {
        useCase = AddOrRemoveFromWishlistUseCase(isProductInWishlistUseCase, wishlistRepository)
    }

    @Test
    fun `Product is not in wishlist, then add method is called`() = runTest {
        coEvery {
            isProductInWishlistUseCase.execute(any())
        } returns false

        useCase.execute("123")

        coVerify {
            wishlistRepository.addToWishlist("123")
        }
    }

    @Test
    fun `Product is in wishlist, then remove method is called`() = runTest {
        coEvery {
            isProductInWishlistUseCase.execute(any())
        } returns true

        useCase.execute("123")

        coVerify {
            wishlistRepository.removeFromWishlist("123")
        }
    }
}