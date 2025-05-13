package com.rahmad.moodvies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rahmad.moodvies.databinding.ItemDetailGenresBinding
import com.rahmad.moodvies.domain.model.Genre
import com.rahmad.moodvies.presentation.adapter.GenresAdapter.Companion.DIFF_CALLBACK

class DetailGenresAdapter :
    ListAdapter<Genre, DetailGenresAdapter.GenresViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val binding =
            ItemDetailGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre)
    }

    inner class GenresViewHolder(private val binding: ItemDetailGenresBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.apply {
                itemGenre.text = genre.genreName
            }
        }
    }
}