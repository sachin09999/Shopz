package com.project.shopz.network.repository

import androidx.navigation.safe.args.generator.ErrorMessage
import com.google.gson.Gson
import com.project.shopz.model.Category
import com.project.shopz.model.Product
import com.project.shopz.network.api.ShopzApi
import retrofit2.Response
import javax.inject.Inject

class ShopzRepository @Inject constructor(
    private val api: ShopzApi
) {

    suspend fun getProduct() : Result<Product> {

        val response = api.getProducts()

        if(response.isSuccessful){
            return Result.success(response.body()!!)
        } else {
            val errMessage = response.errorBody()?.string()
            val obj = Gson().fromJson(errMessage,ErrorMessage::class.java)
            return Result.failure(Exception(obj.message))
        }

    }

//    suspend fun getCategories() : Result<List<Category>> {
//        val response = api.getCategories()
//        if(response.isSuccessful){
//            return Result.success(response.body()!!)
//        } else {
//            val errMessage = response.errorBody()?.string()
//            val obj = Gson().fromJson(errMessage,ErrorMessage::class.java)
//            return Result.failure(Exception(obj.message))
//        }
//    }

    suspend fun getCategories(): Result<List<Category>> = runCatching {
        val response = api.getCategories()
        if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch categories: ${response.message()}")
        }
    }

}