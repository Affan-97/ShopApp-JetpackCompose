package com.affan.shopapp.ui.screen.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.affan.shopapp.common.UiState
import com.affan.shopapp.data.ProductRepository
import com.affan.shopapp.model.OrderItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class OrderViewModel(
    private val repository: ProductRepository
) : ViewModel() {


    private val _uiState: MutableStateFlow<UiState<List<OrderItem>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderItem>>>
        get() = _uiState

    fun getOrders() {
        viewModelScope.launch {
            repository.getOrders().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}