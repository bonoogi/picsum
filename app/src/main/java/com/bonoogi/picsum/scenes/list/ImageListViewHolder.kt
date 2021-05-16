package com.bonoogi.picsum.scenes.list

import androidx.recyclerview.widget.RecyclerView
import com.bonoogi.picsum.data.image.Image
import com.bonoogi.picsum.databinding.ViewImageItemBinding

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
class ImageListViewHolder(
    private val binding: ViewImageItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(image: Image, callback: ImageListCallback) {
        binding.image = image
        binding.root.isClickable = true
        binding.root.setOnClickListener {
            callback.onSelectImage(image.id)
        }
    }
}