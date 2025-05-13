package com.rahmad.moodvies.presentation.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.rahmad.moodvies.R
import com.rahmad.moodvies.data.repository.Result
import com.rahmad.moodvies.databinding.ActivityDetailBinding
import com.rahmad.moodvies.domain.model.DetailMovie
import com.rahmad.moodvies.presentation.adapter.SectionDetailPagerAdapter
import com.rahmad.moodvies.utils.Extensions.showImageInto
import com.rahmad.moodvies.utils.Extensions.toAnotherDate
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var sectionPagerAdapter: SectionDetailPagerAdapter
    private val viewModel by viewModels<DetailViewModel>()
    private val movieId by lazy { intent.getIntExtra(EXTRA_MOVIE_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSystemUI()
        setupToolbar()
        setupViewPager()

        observeDetailMovie()
    }

    private fun setupSystemUI() {
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewPager() {
        sectionPagerAdapter = SectionDetailPagerAdapter(this, movieId)
        binding.viewPager.adapter = sectionPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()
    }

    private fun observeDetailMovie() {
        viewModel.getMovieDetails(movieId).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showShimmer()
                }
                is Result.Success -> {
                    hideShimmer()
                    val detailMovie = result.data
                    detailMovie.populateDetailMovie()
                }

                is Result.Error -> {
                    hideShimmer()
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun DetailMovie.populateDetailMovie() {
        with(binding) {
            viewDetailHeader.apply {
                ivBackdrop.showImageInto(this@DetailActivity, backdropPath)
                ivPoster.showImageInto(this@DetailActivity, posterPath)
                tvTitle.text = title
                tvDateWithDuration.text = getString(
                    R.string.date_with_duration,
                    releaseDate.toAnotherDate(),
                    runtime.toString()
                )
            }

            appBar.addOnOffsetChangedListener { _, verticalOffset ->
                val isCollapsed = abs(verticalOffset) >= appBar.totalScrollRange

                collapsingToolbar.title = if (isCollapsed) title else ""
            }
        }
    }

    private fun showShimmer() {
        binding.apply {
            shimmerDetailHeader.visibility = View.VISIBLE
            shimmerDetailHeader.showShimmer(true)
            appBar.visibility = View.VISIBLE
        }
    }

    private fun hideShimmer() {
        binding.apply {
            shimmerDetailHeader.visibility = View.GONE
            shimmerDetailHeader.showShimmer(false)
            appBar.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.about,
            R.string.reviews
        )
    }
}