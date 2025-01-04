package com.project.shopz.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import com.project.shopz.R

sealed class BottomNavItem(
    val route: String,
    val icon : ImageVector,
    val label : String
) {
    object HomeScreen : BottomNavItem("homescreen", Icons.Default.Home,"Home")
    object Categories : BottomNavItem("categories", Icons.AutoMirrored.Filled.List,"Categories")
    object Cart : BottomNavItem("cart",Icons.Default.ShoppingCart,"Cart")
    object Order : BottomNavItem("order",Icons.Default.Edit,"Order")
}