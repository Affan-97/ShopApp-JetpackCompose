package com.affan.shopapp.model

data class Product(
    val id: Int,
    val name: String,
    val stock: Int,
    val price: Int,
    val desc: String,
    val image: String
)