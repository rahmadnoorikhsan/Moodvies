package com.rahmad.moodvies.presentation.detail.about

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rahmad.moodvies.data.repository.Result
import com.rahmad.moodvies.databinding.FragmentDetailAboutBinding
import com.rahmad.moodvies.domain.model.DetailMovie
import com.rahmad.moodvies.domain.model.Trailer
import com.rahmad.moodvies.presentation.adapter.DetailGenresAdapter
import com.rahmad.moodvies.presentation.adapter.TrailerAdapter
import com.rahmad.moodvies.presentation.detail.trailer.YoutubeActivity
import com.rahmad.moodvies.utils.Extensions.getLanguageName
import com.rahmad.moodvies.utils.Extensions.toDuration
import com.rahmad.moodvies.utils.Extensions.toUSD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailAboutFragment : Fragment() {

    private var _binding: FragmentDetailAboutBinding? = null
    private val binding get() = _binding
    private lateinit var detailGenresAdapter: DetailGenresAdapter
    private lateinit var trailerAdapter: TrailerAdapter
    private val viewModel by viewModels<DetailAboutViewModel>()
    private val movieId: Int by lazy {
        requireArguments().getInt(ARG_MOVIE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailAboutBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGenreAdapter()
        setupTrailerAdapter()

        observeDetailMovie()
        observeTrailersMovie()
    }

    private fun observeDetailMovie() {
        viewModel.getMovieDetails(movieId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT).show()
                    hideProgressBar()
                }

                is Result.Loading -> {
                    showProgressBar()
                }

                is Result.Success -> {
                    val detailMovie = result.data

                    detailMovie.populateMovieDetails()
                    detailGenresAdapter.submitList(detailMovie.genres)

                    hideProgressBar()
                }
            }
        }
    }

    private fun observeTrailersMovie() {
        viewModel.getMovieTrailers(movieId).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {}
                is Result.Success -> {
                    val trailers = result.data
                    if (trailers.isEmpty()) {
                        binding?.apply {
                            tvTrailerNoAvailable.visibility = View.VISIBLE
                            rvDetailTrailers.visibility = View.GONE
                        }
                    } else {
                        binding?.apply {
                            tvTrailerNoAvailable.visibility = View.GONE
                            rvDetailTrailers.visibility = View.VISIBLE
                        }
                        trailerAdapter.submitList(result.data)
                    }
                }
            }
        }
    }

    private fun DetailMovie.populateMovieDetails() {
        binding?.apply {
            if (!tagline.isNullOrEmpty()) {
                tvTagline.text = tagline
                tvTagline.visibility = View.VISIBLE
            } else {
                tvTagline.visibility = View.GONE
            }

            tvOverview.originalText = overview

            viewDetailAboutInformation.apply {
                tvValueLabelOriginal.text = originalTitle
                tvValueStatus.text = status
                tvValueRuntime.text = runtime?.toDuration()
                tvValueCountry.text = productionCountries.joinToString("\n") {
                    "${it.name}"
                }
                tvValueLanguage.text = originalLanguage.getLanguageName()
                tvValueCompanies.text = productionCompanies.joinToString("\n") {
                    "${it.name}"
                }
                tvValueBudget.text = budget?.toUSD()
                tvValueRevenue.text = revenue?.toUSD()
            }
        }
    }

    private fun setupGenreAdapter() {
        detailGenresAdapter = DetailGenresAdapter()
        binding?.rvDetailGenres?.adapter = detailGenresAdapter
    }

    private fun setupTrailerAdapter() {
        trailerAdapter = TrailerAdapter { trailer ->
            navigateToYoutubeActivity(trailer)
        }
        binding?.rvDetailTrailers?.adapter = trailerAdapter
    }

    private fun navigateToYoutubeActivity(trailer: Trailer) {
        val intent = Intent(requireActivity(), YoutubeActivity::class.java)
        intent.putExtra(YoutubeActivity.EXTRA_MOVIE_KEY, trailer.key)
        startActivity(intent)
    }

    private fun showProgressBar() {
        binding?.layoutDetailAbout?.children?.forEach { child ->
            child.visibility = if (child.id == binding?.progressBar?.id) View.VISIBLE else View.GONE
        }
    }

    private fun hideProgressBar() {
        binding?.layoutDetailAbout?.children?.forEach { child ->
            child.visibility = View.VISIBLE
        }
        binding?.progressBar?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_MOVIE_ID = "movie_id"

        fun newInstance(movieId: Int): DetailAboutFragment {
            val fragment = DetailAboutFragment()
            val bundle = Bundle().apply {
                putInt(ARG_MOVIE_ID, movieId)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}