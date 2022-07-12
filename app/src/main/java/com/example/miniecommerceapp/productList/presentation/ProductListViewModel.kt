package com.example.miniecommerceapp.productList.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniecommerceapp.shared.data.repository.ProductRepository
import com.example.miniecommerceapp.shared.wishlist.business.AddOrRemoveFromWishlistUseCase
import com.example.miniecommerceapp.shared.wishlist.business.IsProductInWishlistUseCase
import com.example.miniecommerceapp.shared.wishlist.data.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val isProductInWishlistUseCase: IsProductInWishlistUseCase,
    private val addOrRemoveFromWishlistUseCase: AddOrRemoveFromWishlistUseCase
) :
    ViewModel() {

    private val _viewState = MutableLiveData<ProductListViewState>()
    val viewState: LiveData<ProductListViewState>
        get() = _viewState

    fun loadProductList() {
        viewModelScope.launch {
            _viewState.postValue(ProductListViewState.Loading)

            //data to fetch products
            val productList = repository.getProductList()
            _viewState.postValue(ProductListViewState.Content(productList.map {
                ProductCardViewState(
                    it.productId,
                    it.title,
                    it.description,
                    "US $ ${it.price}",
                    it.imageUrl,
                    isProductInWishlistUseCase.execute(it.productId)
                )
            }))
        }
    }

    fun favoriteIconClicked(productId: String) {
        viewModelScope.launch {
            addOrRemoveFromWishlistUseCase.execute(productId)
            val currentViewState = _viewState.value
            (currentViewState as? ProductListViewState.Content)?.let { content ->
                _viewState.postValue(
                    content.updateFavoriteProduct(
                        productId,
                        isProductInWishlistUseCase.execute(productId)
                    )
                )
            }
        }
    }
}