package com.rohan.productapp.domain.usecase

import com.rohan.productapp.data.repository.products.ProductsRepository
import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.domain.model.Products
import javax.inject.Inject

class ProductsUseCase @Inject constructor(private val productsRepository: ProductsRepository) {
    suspend fun getProducts(): ApiResult<List<Products>> {
        return productsRepository.getProducts()
    }
}