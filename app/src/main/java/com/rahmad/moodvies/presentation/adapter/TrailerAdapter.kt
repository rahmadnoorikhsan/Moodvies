package com.rahmad.moodvies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rahmad.moodvies.databinding.ItemTrailerBinding
import com.rahmad.moodvies.domain.model.Movie
import com.rahmad.moodvies.domain.model.Trailer
import com.rahmad.moodvies.utils.Constants
import com.rahmad.moodvies.utils.Extensions.showImageInto
import com.rahmad.moodvies.utils.Extensions.showThumbnailInto

class TrailerAdapter(
    private val onItemClicked: (Trailer) -> Unit
) : ListAdapter<Trailer, TrailerAdapter.TrailerViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val binding = ItemTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrailerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailer = getItem(position)
        holder.bind(trailer)
    }

    inner class TrailerViewHolder(private val binding: ItemTrailerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trailer: Trailer) {
            binding.apply {
                ivTrailer.showThumbnailInto(itemView.context, trailer.key)
                tvTitleTrailer.text = trailer.name
            }

            itemView.setOnClickListener {
                onItemClicked(trailer)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Trailer>() {
            override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean =
                oldItem.key == newItem.key
        }
    }
}