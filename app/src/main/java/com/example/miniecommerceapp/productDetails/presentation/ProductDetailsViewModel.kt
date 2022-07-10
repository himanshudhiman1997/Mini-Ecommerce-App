package com.example.miniecommerceapp.productDetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniecommerceapp.shared.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private val _viewState = MutableLiveData<ProductDetailsViewState>()

    val viewState: LiveData<ProductDetailsViewState>
        get() = _viewState

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            _viewState.postValue(ProductDetailsViewState.Loading)

            val productDetails = productRepository.getProductDetails(productId)
            _viewState.postValue(ProductDetailsViewState.Content(productDetails))

        }
    }
}