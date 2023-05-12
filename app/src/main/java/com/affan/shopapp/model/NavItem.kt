package com.affan.shopapp.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.affan.shopapp.ui.screen.Screen

class NavItem(
    val title:String,
    val icon: ImageVector,
    val icon_focused: ImageVector,
    val screen: Screen
)