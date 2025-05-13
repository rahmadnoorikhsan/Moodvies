package com.rahmad.moodvies.data.remote.response.images

import com.google.gson.annotations.SerializedName

data class LogosItem(

    @field:SerializedName("aspect_ratio")
    val aspectRatio: Any? = null,

    @field:SerializedName("file_path")
    val filePath: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Any? = null,

    @field:SerializedName("width")
    val width: Int? = null,

    @field:SerializedName("iso_639_1")
    val iso6391: String? = null,

    @field:SerializedName("vote_count")
    val voteCount: Int? = null,

    @field:SerializedName("height")
    val height: Int? = null
)