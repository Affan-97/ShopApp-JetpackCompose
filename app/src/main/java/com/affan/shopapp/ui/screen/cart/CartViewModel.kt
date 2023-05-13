package com.affan.shopapp.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.affan.shopapp.common.UiState
import com.affan.shopapp.data.ProductRepository
import com.affan.shopapp.model.CartItem
import com.affan.shopapp.model.CartState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: ProductRepository
) : ViewModel() {


    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun BoughtItem(Items: List<CartItem>, total: Int) {
        viewModelScope.launch {
            repository.BoughtItem(Items, total).collect{
                if (it){
                    getAddedCart()
                }
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