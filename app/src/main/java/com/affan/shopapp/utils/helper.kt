package com.affan.shopapp.utils

import java.text.NumberFormat
import java.util.Locale

fun convertCurency(price: Int): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return currencyFormat.format(price)
}