package com.rohan.productapp.data.repository.products

import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.domain.model.PicSumPhoto

interface ProductRepository {
    suspend fun getProducts(page: Int, limit: Int): ApiResult<List<PicSumPhoto>>
}