package com.affan.shopapp.model

data class CartState(
    val listCartItem: List<CartItem>,
    val totalPrice: Int
)