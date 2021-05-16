package com.bonoogi.picsum.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
object ImageViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("image_url", "image_corner_radius")
    fun bindImageUrl(imageView: ImageView, url: String?, radius: Int = 0) {
        val builder = Glide.with(imageView).load(url)
        if (radius > 0) builder.transform(RoundedCorners(radius.px))
        builder.into(imageView)
    }
}