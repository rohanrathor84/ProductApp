package com.rohan.productapp.utils

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.rohan.productapp.R

@GlideModule
class MyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // Disk Cache Size (100 MB)
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, 100 * 1024 * 1024))

        // Default Request Options
        builder.setDefaultRequestOptions(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache all versions
                .placeholder(R.drawable.placeholder_color) // Placeholder image
                .error(R.drawable.error_image) // Error image
                .fallback(R.drawable.fallback_image) // Fallback image if URL is null
        )
    }

    /*override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        // Integrate Glide with OkHttp if using OkHttpClient
        val client = OkHttpClient.Builder().build()
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(client)
        )
    }*/
}