package com.rahmad.moodvies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rahmad.moodvies.databinding.ItemGenresBinding
import com.rahmad.moodvies.domain.model.Genre

class GenresAdapter(val data: (Genre) -> Unit) :
    ListAdapter<Genre, GenresAdapter.GenresViewHolder>(DIFF_CALLBACK) {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val binding = ItemGenresBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre, position)
    }

    inner class GenresViewHolder(private val binding: ItemGenresBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre, position: Int) {
            binding.apply {
                itemGenre.apply {
                    text = genre.genreName
                    isChecked = position == selectedPosition

                    setOnClickListener {
                        if (selectedPosition != position) {
                            val previousPosition = selectedPosition
                            selectedPosition = position

                            notifyItemChanged(previousPosition)
                            notifyItemChanged(selectedPosition)

                            data.invoke(genre)
                        }
                    }

                    if (position == 0 && selectedPosition == 0) {
                        binding.itemGenre.post {
                            data.invoke(genre)
                        }
                    }
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean =
                oldItem.id == newItem.id
        }
    }
}