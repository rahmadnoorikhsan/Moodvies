package com.rahmad.moodvies.data.remote.response.images

import com.google.gson.annotations.SerializedName

data class ImagesResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("logos")
	val logos: List<LogosItem>
)
