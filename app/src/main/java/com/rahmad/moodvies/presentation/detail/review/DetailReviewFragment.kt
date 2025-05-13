package com.rahmad.moodvies.presentation.detail.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahmad.moodvies.databinding.FragmentDetailReviewBinding
import com.rahmad.moodvies.presentation.adapter.LoadingStateAdapter
import com.rahmad.moodvies.presentation.adapter.ReviewPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailReviewFragment : Fragment() {

    private var _binding: FragmentDetailReviewBinding? = null
    private val binding get() = _binding
    private lateinit var reviewPagingAdapter: ReviewPagingAdapter
    private val viewModel by viewModels<DetailReviewViewModel>()
    private val movieId: Int by lazy {
        requireArguments().getInt(ARG_MOVIE_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailReviewBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupReviewPagingAdapter()
        observeReviews()
    }

    private fun setupReviewPagingAdapter() {
        reviewPagingAdapter = ReviewPagingAdapter()

        val loadStateAdapter = LoadingStateAdapter { reviewPagingAdapter.retry() }

        binding?.rvReviews?.apply {
            adapter = reviewPagingAdapter.withLoadStateFooter(loadStateAdapter)
            layoutManager = LinearLayoutManager(requireActivity())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            reviewPagingAdapter.loadStateFlow.collectLatest { loadState ->
                binding?.progressBar?.isVisible = loadState.refresh is LoadState.Loading

                val isListEmpty = loadState.refresh is LoadState.NotLoading &&
                        reviewPagingAdapter.itemCount == 0

                binding?.tvEmptyReviews?.isVisible = isListEmpty
                binding?.rvReviews?.isVisible = !isListEmpty
            }
        }
    }

    private fun observeReviews() {
        lifecycleScope.launch {
            viewModel.getMovieReviews(movieId).collectLatest { reviews ->
                reviewPagingAdapter.submitData(lifecycle, reviews)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ARG_MOVIE_ID = "movie_id"

        fun newInstance(movieId: Int): DetailReviewFragment {
            val fragment = DetailReviewFragment()
            val bundle = Bundle().apply {
                putInt(ARG_MOVIE_ID, movieId)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}