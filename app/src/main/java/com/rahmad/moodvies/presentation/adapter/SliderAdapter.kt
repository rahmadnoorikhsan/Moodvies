package com.rahmad.moodvies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rahmad.moodvies.databinding.ItemImageSliderBinding
import com.rahmad.moodvies.domain.model.Movie
import com.rahmad.moodvies.utils.Extensions.showImageInto

class SliderAdapter(val data: (Movie) -> Unit) : ListAdapter<Movie, SliderAdapter.SliderViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val binding = ItemImageSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class SliderViewHolder(private val binding: ItemImageSliderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                itemView.setOnClickListener { data.invoke(movie) }
                ivSlider.showImageInto(itemView.context, movie.backdropPath)
                ivLogo.showImageInto(itemView.context, movie.logo)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id
        }
    }
}