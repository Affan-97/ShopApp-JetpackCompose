package com.affan.shopapp.data

import com.affan.shopapp.model.FakeDataSource
import com.affan.shopapp.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class ProductRepository {
    private val products = mutableListOf<Product>()

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