package com.rohan.productapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.domain.model.Products
import com.rohan.productapp.domain.usecase.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productsUseCase: ProductsUseCase) :
    ViewModel() {
    private val _productsState = MutableStateFlow<ApiResult<List<Products>>?>(null)
    val productsState: StateFlow<ApiResult<List<Products>>?> = _productsState

    fun getProducts() {
        viewModelScope.launch {
            _productsState.value = productsUseCase.getProducts()
        }
    }
}