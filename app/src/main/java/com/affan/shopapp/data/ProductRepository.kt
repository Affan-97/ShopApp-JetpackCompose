package com.affan.shopapp.data

import android.util.Log
import com.affan.shopapp.model.CartItem
import com.affan.shopapp.model.FakeDataSource
import com.affan.shopapp.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class ProductRepository {
    private val products = mutableListOf<Product>()
    private val carts = mutableListOf<CartItem>()

    init {
        if (products.isEmpty()) {
            FakeDataSource.dummyData.forEach {
                products.add(it)
            }
        }
    }

    fun getAllProduct(): Flow<List<Product>> {
        return flowOf(products)
    }

    fun getDetail(itemId: Int): Product {
        return products.first {
            it.id == itemId
        }
    }

    fun getCarts(): Flow<List<CartItem>> {
        return flowOf(carts)
    }

    fun getAddedCart(): Flow<List<CartItem>> {
        return getCarts().map { orderRewards ->
            orderRewards.filter { orderReward ->
                orderReward.qty != 0
            }
        }

    }

    fun addToCart(item: Product, qty: Int): Flow<Boolean> {

        val index = carts.indexOfFirst { it.item == item }
        Log.d("TAG", "addToCart: $index")
        val result = if (index >= 0) {
            val cart = carts[index]
            carts[index] =
                cart.copy(item = cart.item, qty = qty)
            Log.d("TAG", "addToCart: save")
            true
        } else if (index == -1) {
            carts.add(CartItem(item, qty))
            Log.d("TAG", "addToCart: save")
            true
        } else {
            Log.d("TAG", "addToCart: Notsave")
            false
        }


        return flowOf(result)
    }

    fun updateOrderReward(id: Int, qty: Int): Flow<Boolean> {
        val index = carts.indexOfFirst { it.item.id == id }
        val result = if (index >= 0) {
            val cart = carts[index]
            carts[index] =
                cart.copy(item = cart.item, qty = qty)
            Log.d("TAG", "addToCart: save")
            true
        } else {
            false
        }
        Log.d("TAG", "updateOrderReward: $result")
        return flowOf(result)
    }

    fun deleteItem(id: Int): Flow<Boolean> {
        val index = carts.indexOfFirst { it.item.id == id }
        val result = if (index >= 0) {
            carts.removeAt(index)
            true
        } else {
            false
        }
        Log.d("TAG", "updateOrderReward: $result")
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: ProductRepository? = null

        fun getInstance(): ProductRepository =
            instance ?: synchronized(this) {
                ProductRepository().apply {
                    instance = this
                }
            }
    }
}