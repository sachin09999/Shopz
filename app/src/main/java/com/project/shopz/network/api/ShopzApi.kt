package com.project.shopz.network.api

import com.project.shopz.model.Category
import com.project.shopz.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ShopzApi {

    @GET("/api/v1/products")
    suspend fun getProducts() : Response<Product>

    @GET("/api/v1/categories")
    suspend fun getCategories() : Response<List<Category>>
}