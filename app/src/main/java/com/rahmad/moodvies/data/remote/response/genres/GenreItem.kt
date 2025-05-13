package com.rahmad.moodvies.data.remote.response.genres

import com.google.gson.annotations.SerializedName

data class GenreItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val genreName: String
)