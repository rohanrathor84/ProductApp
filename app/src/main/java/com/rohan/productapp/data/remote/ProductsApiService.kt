package com.rohan.productapp.data.remote

import com.rohan.productapp.domain.model.Products
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApiService {

    @GET("products")
    suspend fun getProducts(): Response<List<Products>>
}