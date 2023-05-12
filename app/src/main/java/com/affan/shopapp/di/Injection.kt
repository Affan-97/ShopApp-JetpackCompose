package com.affan.shopapp.di

import com.affan.shopapp.data.ProductRepository


object Injection {
    fun provideRepository(): ProductRepository {
        return ProductRepository.getInstance()
    }
}