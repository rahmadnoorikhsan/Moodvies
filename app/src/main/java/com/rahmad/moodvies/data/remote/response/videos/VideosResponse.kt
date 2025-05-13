package com.rahmad.moodvies.data.remote.response.videos

import com.google.gson.annotations.SerializedName

data class VideosResponse(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: List<VideoItem>
)