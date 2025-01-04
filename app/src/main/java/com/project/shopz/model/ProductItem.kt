package com.project.shopz.model

data class ProductItem(
    val category: Category,
    val creationAt: String,
    val description: String,
    val id: Int,
    val images: List<String>,
    val price: Double,
    val title: String,
    val updatedAt: String
)