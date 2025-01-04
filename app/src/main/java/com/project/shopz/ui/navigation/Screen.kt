package com.project.shopz.ui.navigation

sealed class Screen(val route : String) {
    object HomeScreen : Screen("homescreen")
    object Categories : Screen("categories")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object Order : Screen("order")
    object Checkout : Screen("checkout")
    object Detail : Screen("details/{productId}"){
        fun createRoute(productId : Int) = "details/$productId"
    }
}