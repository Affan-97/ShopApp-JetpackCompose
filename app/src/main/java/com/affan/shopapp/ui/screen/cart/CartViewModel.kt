package com.affan.shopapp.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.affan.shopapp.common.UiState
import com.affan.shopapp.data.ProductRepository
import com.affan.shopapp.model.CartItem
import com.affan.shopapp.model.CartState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _cartState: MutableStateFlow<UiState<List<CartItem>>> =
        MutableStateFlow(UiState.Loading)
    val cartState: StateFlow<UiState<List<CartItem>>>
        get() = _cartState

    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getCart() {
        viewModelScope.launch {
            repository.getCarts().catch {
                _cartState.value = UiState.Error(it.message.toString())
            }.collect {
                _cartState.value = UiState.Success(it)
            }
        }
    }


    fun getAddedCart() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedCart()
                .collect { item ->
                    val totalRequiredPoint =
                        item.sumOf { it.item.price * it.qty }
                    _uiState.value = UiState.Success(CartState(item, totalRequiredPoint))
                }
        }
    }

    fun updateOrderReward(id: Int, count: Int) {
        viewModelScope.launch {
            repository.updateOrderReward(id, count).collect { isUpdated ->
                if (isUpdated) {
                    getAddedCart()
                }
            }
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            repository.deleteItem(id).collect {
                if (it) {
                    getAddedCart()
                }
            }
        }
    }
}