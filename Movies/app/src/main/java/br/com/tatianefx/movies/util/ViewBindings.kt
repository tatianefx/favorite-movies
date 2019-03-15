package br.com.tatianefx.movies.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Tatiane Souza on 14/03/2019.
 */

object ViewBindings {

    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun bindImageViewUrl(imageView: ImageView, imageUrl: String?) {

        if (!imageUrl.isNullOrEmpty()) {
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(85,100)

            Glide.with(imageView)
                .load(imageUrl)
                .thumbnail(Glide.with(imageView.context).load(imageUrl).apply(requestOptions))
                .into(imageView)
        }
    }

    @BindingAdapter("app:imageUrlLarge")
    @JvmStatic
    fun bindImageViewUrlLarge(imageView: ImageView, imageUrl: String?) {

        if (!imageUrl.isNullOrEmpty()) {
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            Glide.with(imageView)
                .load(imageUrl)
                .thumbnail(Glide.with(imageView.context).load(imageUrl).apply(requestOptions))
                .into(imageView)
        }
    }
}