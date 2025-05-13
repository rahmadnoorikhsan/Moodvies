package com.rahmad.moodvies.presentation.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.rahmad.moodvies.R
import com.rahmad.moodvies.data.repository.Result
import com.rahmad.moodvies.databinding.ActivityMainBinding
import com.rahmad.moodvies.presentation.adapter.GenresAdapter
import com.rahmad.moodvies.presentation.adapter.LoadingStateAdapter
import com.rahmad.moodvies.presentation.adapter.MoviePagingAdapter
import com.rahmad.moodvies.presentation.adapter.ShimmerMovieAdapter
import com.rahmad.moodvies.presentation.adapter.SliderAdapter
import com.rahmad.moodvies.presentation.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var genresAdapter: GenresAdapter
    private lateinit var moviePagingAdapter: MoviePagingAdapter
    private var selectedGenreId: Int? = null
    private var isFirstPagingLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupSystemUI()
        setupToolbar()

        setupSliderAdapter()
        observePopularMovies()

        setupGenreAdapter()
        observeGenres()
        setupMoviePagingAdapter()
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

        binding.appBar.addOnOffsetChangedListener { _, verticalOffset ->
            val isCollapsed = abs(verticalOffset) >= binding.appBar.totalScrollRange

            binding.collapsingToolbar.title = if (isCollapsed) getString(R.string.app_name) else ""
        }
    }

    private fun setupSliderAdapter() {
        sliderAdapter = SliderAdapter { movie ->
            navigateToDetail(movie.id)
        }

        binding.vpImage.apply {
            adapter = sliderAdapter
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    swipe()
                }
            }
        }
    }

    private fun observePopularMovies() {
        viewModel.getPopularMovies().observe(this) { result ->
            when (result) {
                is Result.Error -> {
                    hideShimmer()
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    showShimmer()
                }

                is Result.Success -> {
                    hideShimmer()
                    sliderAdapter.submitList(result.data.take(5))
                }
            }
        }
    }

    private tailrec suspend fun ViewPager2.swipe() {
        delay(3000L)
        val numberItems = adapter?.itemCount ?: 0
        val lastIndex = if (numberItems > 0) numberItems - 1 else 0
        val nextItem = if (currentItem == lastIndex) 0 else currentItem + 1
        setCurrentItem(nextItem, true)
        swipe()
    }

    private fun setupGenreAdapter() {
        genresAdapter = GenresAdapter { selectedGenre ->
            selectedGenreId = selectedGenre.id
            observeMoviesByGenre(selectedGenre.id.toString())
        }
        binding.rvGenres.apply {
            adapter = genresAdapter
        }
    }

    private fun observeGenres() {
        viewModel.getAllGenres().observe(this) { result ->
            when (result) {
                is Result.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {}
                is Result.Success -> {
                    genresAdapter.submitList(result.data)
                }
            }
        }
    }

    private fun setupMoviePagingAdapter() {
        moviePagingAdapter = MoviePagingAdapter { movie ->
            navigateToDetail(movie.id)
        }

        val loadStateAdapter = LoadingStateAdapter { moviePagingAdapter.retry() }
        val gridCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 5

        binding.rvMovies.apply {
            adapter = moviePagingAdapter.withLoadStateFooter(loadStateAdapter)
            layoutManager = GridLayoutManager(this@MainActivity, gridCount)
        }

        lifecycleScope.launch {
            moviePagingAdapter.loadStateFlow.collectLatest { loadState ->
                val isLoading = loadState.refresh is LoadState.Loading
                showListShimmer(isLoading)
            }
        }
    }

    private fun observeMoviesByGenre(genreId: String) {
        lifecycleScope.launch {
            viewModel.getMoviesByGenre(genreId).collectLatest { movies ->
                moviePagingAdapter.submitData(lifecycle, movies)
            }
        }
    }

    private fun showShimmer() {
        binding.itemHeaderMainShimmer.shimmerHeader.apply {
            visibility = View.VISIBLE
            showShimmer(true)
        }
        binding.appBar.visibility = View.INVISIBLE
    }

    private fun hideShimmer() {
        binding.itemHeaderMainShimmer.shimmerHeader.apply {
            visibility = View.INVISIBLE
            stopShimmer()
        }
        binding.appBar.visibility = View.VISIBLE
    }

    private fun showListShimmer(show: Boolean) {
        val shimmerAdapter = ShimmerMovieAdapter()
        if (show) {
            binding.rvMovies.adapter = shimmerAdapter
        } else {
            val loadStateAdapter = LoadingStateAdapter { moviePagingAdapter.retry() }
            binding.rvMovies.adapter = moviePagingAdapter.withLoadStateFooter(loadStateAdapter)
        }
    }

    private fun navigateToDetail(movieId: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieId)
        startActivity(intent)
    }
}