package com.rohan.productapp.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rohan.productapp.di.ApiResult
import com.rohan.productapp.domain.model.PicSumPhoto
import com.rohan.productapp.domain.usecase.ProductUseCase

class ProductPagingSource(private val productUseCase: ProductUseCase, private val pageSize: Int) :
    PagingSource<Int, PicSumPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, PicSumPhoto>): Int? {
        // Use the closest anchor position to determine the key for refreshing
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PicSumPhoto> {
        val currentPage = params.key ?: 1

        return try {
            when (val response = productUseCase.getProducts(page = currentPage, limit = pageSize)) {
                is ApiResult.Success -> {
                    val products = response.data

                    // Return the loaded page data
                    LoadResult.Page(
                        data = products,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = if (products.isEmpty()) null else currentPage + 1
                    )
                }

                is ApiResult.Error -> {
                    // Return an error if the API call fails
                    LoadResult.Error(Exception(response.error.message))
                }

                is ApiResult.NetworkError -> {
                    // Handle network errors separately
                    LoadResult.Error(Exception("Network Error"))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}