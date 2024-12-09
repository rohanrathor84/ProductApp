package com.rohan.productapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.rohan.productapp.domain.usecase.ProductUseCase
import com.rohan.productapp.utils.ProductPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productUseCase: ProductUseCase) :
    ViewModel() {

    val products = Pager(
        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
        pagingSourceFactory = { ProductPagingSource(productUseCase) }
    ).flow.cachedIn(viewModelScope)
}