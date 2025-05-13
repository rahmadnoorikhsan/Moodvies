package com.rahmad.moodvies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rahmad.moodvies.databinding.ItemReviewBinding
import com.rahmad.moodvies.domain.model.Review
import com.rahmad.moodvies.utils.Extensions.toFullDate

class ReviewPagingAdapter : PagingDataAdapter<Review, ReviewPagingAdapter.ReviewViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewPagingAdapter.ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewPagingAdapter.ReviewViewHolder, position: Int) {
        val review = getItem(position) ?: return
        holder.bind(review)
    }

    inner class ReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.apply {
                tvAuthor.text = review.author
                tvDate.text = review.date?.toFullDate()
                tvContent.originalText = review.content ?: ""
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean =
                oldItem.id == newItem.id
        }
    }
}