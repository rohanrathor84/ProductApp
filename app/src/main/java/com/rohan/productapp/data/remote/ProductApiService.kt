package com.rohan.productapp.data.remote

import com.rohan.productapp.domain.model.PicSumPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApiService {

    /**
     * Fetches the list of products (images) with pagination.
     *
     * @param page The page number to fetch.
     * @param limit The number of items per page.
     * @return A Response containing a list of products.
     */
    @GET("v2/list")
    suspend fun getProducts(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<List<PicSumPhoto>>
}