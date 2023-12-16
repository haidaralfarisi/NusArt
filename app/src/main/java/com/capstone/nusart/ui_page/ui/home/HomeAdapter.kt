package com.capstone.nusart.ui_page.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.capstone.nusart.R
import com.capstone.nusart.data.api.response.ListArt
import com.capstone.nusart.databinding.ItemListBinding

class HomeAdapter(private val listener: Listener) :
    PagingDataAdapter<ListArt, HomeAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    inner class MyViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListArt) {
            val radius = 24

            val requestOptions = RequestOptions()
                .transform(RoundedCorners(radius))
                .placeholder(android.R.color.darker_gray)
                .error(R.drawable.baseline_broken_image_24)

            Glide.with(binding.imgHome.context)
                .load(item.imageurl)
                .apply(requestOptions)
                .into(binding.imgHome)

            binding.tvPaintname.text = item.title
            binding.tvPaintgenre.text = item.genre

            binding.root.setOnClickListener {
                listener.onListener(item)
            }
        }
    }

    interface Listener {
        fun onListener(story: ListArt)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListArt>() {
            override fun areItemsTheSame(oldItem: ListArt, newItem: ListArt): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListArt,
                newItem: ListArt
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
