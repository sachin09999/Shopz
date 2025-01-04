package com.project.shopz.ui.screens.CartManager

import com.project.shopz.model.ProductItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object CartManager {

    private val _cartItems = MutableStateFlow<List<ProductItem>>(emptyList())
    val cartItems : StateFlow<List<ProductItem>> get() = _cartItems

    fun addItem(item: ProductItem) {
        _cartItems.value = _cartItems.value + item
    }

    fun removeItem(item: ProductItem) {
        _cartItems.value = _cartItems.value - item
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }


}