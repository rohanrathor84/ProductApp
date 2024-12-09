package com.rohan.productapp.presentation.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rohan.productapp.R
import com.rohan.productapp.domain.model.PicSumPhoto
import com.rohan.productapp.utils.placeholderColors

class ProductAdapter :
    PagingDataAdapter<PicSumPhoto, ProductAdapter.ProductViewHolder>(ProductComparator) {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productImage: ImageView = view.findViewById(R.id.productImage)
        val productTitle: TextView = view.findViewById(R.id.productTitle)
        val productPrice: TextView = view.findViewById(R.id.productPrice)
        val productRating: TextView = view.findViewById(R.id.productRating)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        product?.let {
            // Use the color based on the position
            val placeholderColor = placeholderColors[position % placeholderColors.size]

            Glide.with(holder.productImage.context)
                .load(it.download_url)
                .placeholder(ColorDrawable(Color.parseColor(placeholderColor)))
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Enable caching
                .into(holder.productImage)

            holder.productTitle.text = it.author
            holder.productPrice.text = "$${10}"
            holder.productRating.text = "Rating: ${4.5} (${420})"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    object ProductComparator : DiffUtil.ItemCallback<PicSumPhoto>() {
        override fun areItemsTheSame(oldItem: PicSumPhoto, newItem: PicSumPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PicSumPhoto, newItem: PicSumPhoto): Boolean {
            return oldItem == newItem
        }
    }
}