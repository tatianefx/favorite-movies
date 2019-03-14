package br.com.tatianefx.movies.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Created by Tatiane Souza on 14/03/2019.
 */

object ViewBindings {

    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun bindImageViewUrl(imageView: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(imageView)
                .load(imageUrl)
                .into(imageView)
        }
    }
}