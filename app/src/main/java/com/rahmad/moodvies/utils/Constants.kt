package com.rahmad.moodvies.utils

import com.rahmad.moodvies.BuildConfig

object Constants {
    private const val BEARER = "Bearer "
    const val AUTHORIZATION = "Authorization"

    fun getBearer(): String {
        return BEARER + BuildConfig.API_KEY
    }

    const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
    const val YOUTUBE_THUMBNAIL_URL_ENDPOINT = "/sddefault.jpg"
}