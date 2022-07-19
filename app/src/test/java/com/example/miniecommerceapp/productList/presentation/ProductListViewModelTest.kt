package com.example.miniecommerceapp.productList.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.miniecommerceapp.productList.business.Product
import com.example.miniecommerceapp.shared.data.repository.ProductRepository
import com.example.miniecommerceapp.shared.wishlist.business.AddOrRemoveFromWishlistUseCase
import com.example.miniecommerceapp.shared.wishlist.business.IsProductInWishlistUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
internal class ProductListViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ProductListViewModel
    private val repository = mockk<ProductRepository>()
    private val isProductInWishlistUseCase = mockk<IsProductInWishlistUseCase>()
    private val addOrRemoveFromWishlistUseCase = mockk<AddOrRemoveFromWishlistUseCase>()

    private val listOfProducts = (0..2).map {
        Product("title", "description", 6.0, "", "id-$it")
    }

    @Before
    fun setUp() {
        coEvery {
            isProductInWishlistUseCase.execute(any())
        } returns false

        coEvery {
            isProductInWishlistUseCase.execute("id-1")
        } returns true

        coEvery {
            repository.getProductList()
        } returns listOfProducts

        viewModel = ProductListViewModel(
            repository,
            isProductInWishlistUseCase,
            addOrRemoveFromWishlistUseCase,
            dispatcher
        )
    }

    @Test
    fun `Load method correctly creates the ViewState`() = runTest {
        val values = mutableListOf<ProductListViewState>()
        viewModel.viewState.observeForever {
            values.add(it)
        }
        viewModel.loadProductList()

        dispatcher.scheduler.advanceUntilIdle()

        assert(values[0] is ProductListViewState.Loading)
        assert(values[1] == ProductListViewState.Content((0..2).map {
            ProductCardViewState(
                "id-$it",
                "title",
                "description",
                "US $ 6.0",
                "",
                it == 1
            )
        }))
    }
}