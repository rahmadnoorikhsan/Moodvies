package com.rahmad.moodvies.utils

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.rahmad.moodvies.BuildConfig
import com.rahmad.moodvies.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Extensions {
    fun ImageView.showImageInto(context: Context, url: String?) {
        Glide.with(context)
            .load(BuildConfig.BASE_URL_IMAGE + url)
            .placeholder(ContextCompat.getDrawable(context, R.color.dark))
            .into(this)
    }

    fun ImageView.showThumbnailInto(context: Context, key: String?) {
        Glide.with(context)
            .load(Constants.YOUTUBE_THUMBNAIL_URL + key + Constants.YOUTUBE_THUMBNAIL_URL_ENDPOINT)
            .placeholder(ContextCompat.getDrawable(context, R.color.dark))
            .into(this)
    }

    fun String.toAnotherDate(): String =
        try {
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date = df.parse(this) as Date
            SimpleDateFormat("MMM yyyy", Locale.US).format(date)
        } catch (e: Exception) {
            "-"
        }

    fun String.toFullDate(): String =
        try {
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date = df.parse(this) as Date
            SimpleDateFormat("dd MMMM yyyy", Locale.US).format(date)
        } catch (e: Exception) {
            "-"
        }

    fun formatOneDecimal(value: Double): String {
        return String.format(Locale.US, "%.1f", value)
    }

    fun Int.toDuration(): String =
        if(this < 60){
            this.toString() + "min"
        }else{
            val time = this/60
            val hours = time.toString().substringBefore(".").toInt()
            val minutes = this - (hours * 60)
            hours.toString() +
                    " hrs " +
                    minutes.toString() +
                    " mins"
        }

    fun Int?.toUSD(): String {
        val format = NumberFormat.getNumberInstance(Locale.US)
        format.minimumFractionDigits = 0
        format.maximumFractionDigits = 0
        return  if (this == null || this == 0) "-" else "$" + format.format(this)
    }

    fun String?.getLanguageName(): String {
        if (this.isNullOrBlank()) return ""
        val locale = Locale(this)
        return locale.getDisplayLanguage(Locale.ENGLISH).replaceFirstChar { it.uppercase() }
    }
}