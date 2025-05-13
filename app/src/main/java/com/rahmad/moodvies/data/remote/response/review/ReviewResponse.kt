package com.rahmad.moodvies.data.remote.response.review

import com.google.gson.annotations.SerializedName

data class ReviewResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<ReviewItem>,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)