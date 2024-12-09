package com.rohan.productapp.presentation.ui.products

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rohan.productapp.R
import com.rohan.productapp.presentation.adapter.ProductAdapter
import com.rohan.productapp.presentation.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        setupRecyclerView()

        lifecycleScope.launch {
            viewModel.products.collectLatest { pagingData ->
                productAdapter.submitData(pagingData)
            }
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewProducts)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ProductActivity)
            adapter = productAdapter
        }
    }
}