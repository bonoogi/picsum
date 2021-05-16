package com.bonoogi.picsum.scenes.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bonoogi.picsum.R
import com.bonoogi.picsum.data.image.Image
import com.bonoogi.picsum.databinding.ViewImageItemBinding

/**
 * @author 구본욱(bnoo1333@gmail.com)
 */
interface ImageListCallback {
    fun onSelectImage(id: String)
}

class ImageListAdapter(
    private val callback: ImageListCallback
) : ListAdapter<Image, ImageListViewHolder>(object : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        val binding = DataBindingUtil.inflate<ViewImageItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.view_image_item,
            parent,
            false
        )
        return ImageListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, callback)
    }
}