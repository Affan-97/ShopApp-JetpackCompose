package com.affan.shopapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.affan.shopapp.common.UiState
import com.affan.shopapp.data.ProductRepository
import com.affan.shopapp.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ProductRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Product>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Product>>
        get() = _uiState


    fun getDetail(itemId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getDetail(itemId))
        }
    }
}