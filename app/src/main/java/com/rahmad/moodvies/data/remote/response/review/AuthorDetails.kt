package com.rahmad.moodvies.data.remote.response.review

import com.google.gson.annotations.SerializedName

data class AuthorDetails(

    @field:SerializedName("avatar_path")
    val avatarPath: Any? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("rating")
    val rating: Int? = null,

    @field:SerializedName("username")
    val username: String? = null
)
