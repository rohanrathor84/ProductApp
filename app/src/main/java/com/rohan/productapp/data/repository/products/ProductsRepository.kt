package com.rohan.productapp.data.repository.products

import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.domain.model.Products

interface ProductsRepository {
    suspend fun getProducts(): ApiResult<List<Products>>
}