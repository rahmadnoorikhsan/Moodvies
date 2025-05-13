package com.rahmad.moodvies.presentation.detail.trailer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.rahmad.moodvies.databinding.ActivityYoutubeBinding

class YoutubeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYoutubeBinding
    private val movieKey by lazy { intent.getStringExtra(EXTRA_MOVIE_KEY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupYoutubePlayer()
        setupCloseButton()
    }

    private fun setupCloseButton() {
        binding.ivClose.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupYoutubePlayer() {
        lifecycle.addObserver(binding.youtubePlayer)

        binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                movieKey?.let { key ->
                    youTubePlayer.loadVideo(key, 0f)
                }
            }
        })
    }

    companion object {
        const val EXTRA_MOVIE_KEY = "extra_movie_key"
    }
}