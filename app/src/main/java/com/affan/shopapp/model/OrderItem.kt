package com.affan.shopapp.model

data class OrderItem(
    val listOrderItem: List<CartItem>,
    val totalPrice: Int
)