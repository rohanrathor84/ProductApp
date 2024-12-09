package com.rohan.productapp.data.repository.products

import com.rohan.productapp.data.remote.ProductApiService
import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.di.safeApiCall
import com.rohan.productapp.domain.model.PicSumPhoto

class ProductRepositoryImpl(private val productApiService: ProductApiService) :
    ProductRepository {
    override suspend fun getProducts(page: Int, limit: Int): ApiResult<List<PicSumPhoto>> {
        return safeApiCall { productApiService.getProducts(page, limit) }
    }
}