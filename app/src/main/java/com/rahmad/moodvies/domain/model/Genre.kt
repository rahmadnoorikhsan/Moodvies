package com.rahmad.moodvies.domain.model

data class Genre(
    val id: Int,
    val genreName: String,
    var isSelected: Boolean = false
)