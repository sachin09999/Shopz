package com.project.shopz.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.shopz.model.Category
import com.project.shopz.model.Product
import com.project.shopz.network.repository.ShopzRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopzViewModel @Inject constructor(private val repository: ShopzRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()


    init {
        Log.d("ShopzViewModel", "Initializing ViewModel")

        getProduct() // Initial load
        getCategories()

    }

    private fun getProduct() = viewModelScope.launch {
        Log.d("ShopzViewModel", "Starting getProduct, setting loading to true")
        _uiState.update { it.copy(isLoading = true) }

        try {
            Log.d("ShopzViewModel", "Calling repository.getProduct()")
            val response = repository.getProduct()
            Log.d("ShopzViewModel", "Repository response received: $response")

            if (response.isSuccess) {
                _uiState.update { it.copy(
                    data = response.getOrThrow(),
                    isLoading = false,
                    error = ""
                ) }
                Log.d("ShopzViewModel", "Success: Updated UI state with data")
            } else {
                _uiState.update { it.copy(
                    error = response.exceptionOrNull()?.message ?: "Unknown error",
                    isLoading = false,
                    data = null
                ) }
                Log.d("ShopzViewModel", "Error: ${response.exceptionOrNull()?.message}")
            }
        } catch (e: Exception) {
            Log.e("ShopzViewModel", "Exception in getProduct", e)
            _uiState.update { it.copy(
                error = e.message ?: "An error occurred",
                isLoading = false,
                data = null
            ) }
        }
    }

    private fun getCategories() = viewModelScope.launch {
        try {
            val response = repository.getCategories()
            if (response.isSuccess) {
                _categories.value = response.getOrThrow()
                Log.d("ShopzViewModel", "Categories loaded successfully")
            } else {
                Log.e("ShopzViewModel", "Error: ${response.exceptionOrNull()?.message}")
            }
        } catch (e: Exception) {
            Log.e("ShopzViewModel", "Error fetching categories", e)
        }
    }

}

data class UiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: Product? = null
)