package com.example.miniecommerceapp.productList.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniecommerceapp.shared.data.repository.ProductRepository
import com.example.miniecommerceapp.shared.wishlist.data.repository.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val wishlistRepository: WishlistRepository
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
                    it.title,
                    it.description,
                    "US $ ${it.price}",
                    it.imageUrl,
                    wishlistRepository.isFavorite(it.productId)
                )
            }))
        }
    }
}