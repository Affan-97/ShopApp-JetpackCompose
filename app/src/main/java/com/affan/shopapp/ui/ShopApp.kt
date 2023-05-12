package com.affan.shopapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.affan.shopapp.model.NavItem
import com.affan.shopapp.ui.component.BottomBar
import com.affan.shopapp.ui.screen.Screen
import com.affan.shopapp.ui.screen.cart.CartScreen
import com.affan.shopapp.ui.screen.home.HomeScreen
import com.affan.shopapp.ui.screen.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopApp(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route



    Scaffold(bottomBar = { BottomBar(navController = navController) }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.routes,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screen.Home.routes) {
                HomeScreen(navigateDetail = {})
            }
            composable(Screen.Cart.routes) {
                val context = LocalContext.current
                CartScreen()
            }
            composable(Screen.Profile.routes) {
                ProfileScreen()
            }

        }

    }
}

