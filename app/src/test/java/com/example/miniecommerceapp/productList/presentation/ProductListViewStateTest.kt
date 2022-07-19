package com.example.miniecommerceapp.productList.presentation

import org.junit.Test

internal class ProductListViewStateTest {

    @Test
    fun `Correct product view state is updated`() {

        //providing the data
        val content = ProductListViewState.Content(
            productList = (0..9).map {
                ProductCardViewState(
                    "id$it",
                    "",
                    "",
                    "",
                    "",
                    false
                )
            }
        )

        val result = content.updateFavoriteProduct("id4", true)

        assert(!result.productList[3].isFavorite)
        assert(result.productList[4].isFavorite)
        assert(!result.productList[5].isFavorite)
    }
}