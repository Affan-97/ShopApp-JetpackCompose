package com.affan.shopapp.ui.screen

sealed class Screen(val routes:String) {


    object Home: Screen("home")
    object Cart: Screen("cart")
    object Profile: Screen("profile")

    object Detail:Screen("home/{id}"){
        fun createRoute(id:Int) = "home/$id"
    }

}