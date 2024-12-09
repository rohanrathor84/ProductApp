package com.rohan.productapp.presentation.ui.products

import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rohan.productapp.R
import com.rohan.productapp.presentation.adapter.LoadingStateAdapter
import com.rohan.productapp.presentation.adapter.ProductAdapter
import com.rohan.productapp.presentation.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var emptyView: ImageView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        emptyView = findViewById(R.id.productEmptyView)
        progressBar = findViewById(R.id.productProgressBar)
        setupRecyclerView()

        lifecycleScope.launch {
            viewModel.products.collectLatest { pagingData ->
                productAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter()
        val footerAdapter = LoadingStateAdapter { productAdapter.retry() }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProducts)
        recyclerView.apply {
            recyclerView.layoutManager = GridLayoutManager(this@ProductActivity, 2)
            val concatAdapter = productAdapter.withLoadStateFooter(footerAdapter)
            adapter = concatAdapter
            setHasFixedSize(true)
            // Enable preloading for smoother scrolling
            setItemViewCacheSize(20)
        }

        productAdapter.addLoadStateListener { loadState ->
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && productAdapter.itemCount == 0

            // Show empty view
            emptyView.isVisible = isListEmpty

            // Show full-screen progress bar
            progressBar.isVisible = loadState.refresh is LoadState.Loading

            // Handle error
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error

            errorState?.let {
                Toast.makeText(this, it.error.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}