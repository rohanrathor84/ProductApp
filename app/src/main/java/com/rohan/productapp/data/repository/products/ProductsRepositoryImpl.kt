package com.rohan.productapp.data.repository.products

import com.rohan.productapp.data.remote.ProductsApiService
import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.di.safeApiCall
import com.rohan.productapp.domain.model.Products

class ProductsRepositoryImpl(private val productsApiService: ProductsApiService) :
    ProductsRepository {
    override suspend fun getProducts(): ApiResult<List<Products>> {
        return safeApiCall { productsApiService.getProducts() }
    }
}