package com.affan.shopapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.affan.shopapp.ui.component.BottomBar
import com.affan.shopapp.ui.screen.Screen
import com.affan.shopapp.ui.screen.cart.CartScreen
import com.affan.shopapp.ui.screen.detail.DetailScreen
import com.affan.shopapp.ui.screen.home.HomeScreen
import com.affan.shopapp.ui.screen.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopApp(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route



    Scaffold(bottomBar = {
        if (currentRoute != Screen.Detail.routes) {
            BottomBar(navController = navController)
        }
    }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.routes,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screen.Home.routes) {
                HomeScreen(navigateDetail = {
                    navController.navigate(Screen.Detail.createRoute(it))
                })
            }
            composable(Screen.Cart.routes) {
                val context = LocalContext.current
                CartScreen( navigateBack = {
                    navController.navigateUp()
                })
            }
            composable(Screen.Profile.routes) {
                ProfileScreen()
            }
            composable(
                Screen.Detail.routes,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                val idProduct = it.arguments?.getInt("id") ?: -1
                DetailScreen(id = idProduct, navigateBack = {
                    navController.navigateUp()
                }, navigateToCart = {
                    navController.popBackStack()
                    navController.navigate(Screen.Cart.routes) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }

        }

    }
}

