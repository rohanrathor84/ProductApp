package com.rohan.productapp.domain.usecase

import com.rohan.productapp.data.repository.products.ProductRepository
import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.domain.model.PicSumPhoto
import javax.inject.Inject

class ProductUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun getProducts(page: Int, limit: Int): ApiResult<List<PicSumPhoto>> {
        return productRepository.getProducts(page, limit)
    }
}