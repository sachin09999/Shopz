package com.project.shopz.ui.navigation

import DetailsScreen
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.shopz.model.ProductItem
import com.project.shopz.ui.screens.Cart
import com.project.shopz.ui.screens.CartManager.CartManager
import com.project.shopz.ui.screens.Categories
import com.project.shopz.ui.screens.CheckoutScreen
import com.project.shopz.ui.screens.HomeScreen
import com.project.shopz.ui.screens.OrderScreen
import com.project.shopz.ui.screens.Profile
import com.project.shopz.viewmodel.ShopzViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: ShopzViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {

        //HomeScreen
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                viewModel = viewModel,
                onProductClick = { productId ->
                    navController.navigate(Screen.Detail.createRoute(productId))
                },
                navController
            )
        }

        composable(route = Screen.Categories.route) {
            Categories(viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Cart.route) {
            Cart(onBackClick = { navController.popBackStack() },
                onOrderClick = { navController.navigate("order") })
        }

        composable(route = Screen.Profile.route) {
            Profile()
        }

        composable(Screen.Order.route) {
            OrderScreen(
                cartItems = CartManager.cartItems.value,
                onCheckoutClick = { totalPrice ->
                    navController.navigate(Screen.Checkout.route + "/$totalPrice")
                }
            )
        }
        composable(Screen.Checkout.route + "/{totalPrice}") { backStackEntry ->
            val totalPrice = backStackEntry.arguments?.getString("totalPrice")?.toDoubleOrNull() ?: 0.0
            CheckoutScreen(
                totalPrice = totalPrice,
                onOrderComplete = { navController.navigate("homescreen") }
            )
        }

        composable(route = Screen.Detail.route,
            arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
//            DetailsScreen(
//                product = ProductItem,
//                productid = productId,
//                onBackClick = {
//                    navController.popBackStack()
//                },
//                onOrderClick = {
//
//                }
//            )

        }


    }

}

