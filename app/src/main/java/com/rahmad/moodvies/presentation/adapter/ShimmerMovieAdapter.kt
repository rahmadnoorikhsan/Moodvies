package com.rahmad.moodvies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.rahmad.moodvies.databinding.ItemMovieShimmerBinding

class ShimmerMovieAdapter(private val itemCount: Int = 9) :
    RecyclerView.Adapter<ShimmerMovieAdapter.ShimmerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
        val binding =
            ItemMovieShimmerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShimmerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {
        val shimmerLayout = holder.itemView as ShimmerFrameLayout
        shimmerLayout.startShimmer()
    }

    override fun getItemCount(): Int = itemCount

    class ShimmerViewHolder(binding: ItemMovieShimmerBinding) :
        RecyclerView.ViewHolder(binding.root)
}