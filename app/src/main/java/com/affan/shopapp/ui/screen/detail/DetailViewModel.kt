package com.affan.shopapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.affan.shopapp.common.UiState
import com.affan.shopapp.data.ProductRepository
import com.affan.shopapp.model.CartItem
import com.affan.shopapp.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Product>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Product>>
        get() = _uiState

    private val _cartState: MutableStateFlow<UiState<List<CartItem>>> =
        MutableStateFlow(UiState.Loading)
    val cartState: StateFlow<UiState<List<CartItem>>>
        get() = _cartState

    fun getCart() {
        viewModelScope.launch {
            repository.getCarts()
                .catch {
                    _cartState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _cartState.value = UiState.Success(it)
                }
        }
    }

    fun getDetail(itemId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getDetail(itemId))
        }
    }

    fun addToCart(product: Product, count: Int) {
        viewModelScope.launch {
            repository.addToCart(product, count)
        }
    }
}