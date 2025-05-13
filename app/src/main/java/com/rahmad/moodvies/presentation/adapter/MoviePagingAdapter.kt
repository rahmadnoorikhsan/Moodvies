package com.rahmad.moodvies.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rahmad.moodvies.databinding.ItemMovieBinding
import com.rahmad.moodvies.domain.model.Movie
import com.rahmad.moodvies.presentation.adapter.SliderAdapter.Companion.DIFF_CALLBACK
import com.rahmad.moodvies.utils.Extensions.formatOneDecimal
import com.rahmad.moodvies.utils.Extensions.showImageInto

class MoviePagingAdapter(
    private val data: (Movie) -> Unit
) : PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviePagingAdapter.MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviePagingAdapter.MovieViewHolder, position: Int) {
        val movie = getItem(position) ?: return
        holder.bind(movie)
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                ivMovie.showImageInto(itemView.context, movie.posterPath)
                if (movie.voteAverage != 0.0) {
                    tvRating.text = formatOneDecimal(movie.voteAverage)
                } else {
                    tvRating.visibility = View.GONE
                }
                ivMovie.setOnClickListener {
                    data.invoke(movie)
                }
            }
        }
    }
}