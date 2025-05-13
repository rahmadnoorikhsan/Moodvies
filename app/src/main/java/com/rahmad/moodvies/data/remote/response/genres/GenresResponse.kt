package com.rahmad.moodvies.data.remote.response.genres

import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres")
    val genres: List<GenreItem>
)