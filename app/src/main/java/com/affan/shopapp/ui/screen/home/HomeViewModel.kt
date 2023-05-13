package com.affan.shopapp.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

class HomeViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Product>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Product>>>
        get() = _uiState
    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

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

    fun getAllData() {
        viewModelScope.launch {
            repository.getAllProduct()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.searchProduct(_query.value)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }


    }
}