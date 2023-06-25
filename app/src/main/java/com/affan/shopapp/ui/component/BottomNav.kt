package com.affan.shopapp.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.affan.shopapp.R
import com.affan.shopapp.model.NavItem
import com.affan.shopapp.ui.screen.Screen

@Composable
fun BottomBar(navController: NavHostController) {
    val navItems = listOf(
        NavItem(
            title = stringResource(R.string.home_tag),
            icon = Icons.Default.Home,
            icon_focused = Icons.Outlined.Home,
            icon_desc = stringResource(R.string.home_page),
            screen = Screen.Home
        ),
        NavItem(
            title = stringResource(R.string.transaction_tag),
            icon = Icons.Default.DateRange,
            icon_focused = Icons.Outlined.DateRange,
            icon_desc = stringResource(R.string.transaction_page),
            screen = Screen.Transaction
        ),

        NavItem(
            title = stringResource(R.string.profile_tag),
            icon = Icons.Default.AccountCircle,
            icon_focused = Icons.Outlined.AccountCircle,
            icon_desc = stringResource(R.string.about_page),
            screen = Screen.Profile
        )
    )
    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination
    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
            .background(Color.Transparent)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        navItems.forEach { navItem ->
            BottomBarItem(
                navItem = navItem,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun BottomBarItem(
    navItem: NavItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == navItem.screen.routes } == true

    val background =
        if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.6f) else Color.Transparent

    val contentColor =
        if (selected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {
                navController.navigate(navItem.screen.routes) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = if (selected) navItem.icon_focused else navItem.icon,
                contentDescription = navItem.icon_desc,
                tint = contentColor
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = navItem.title,
                    color = contentColor
                )
            }
        }
    }
}