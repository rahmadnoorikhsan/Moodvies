package com.rahmad.moodvies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rahmad.moodvies.data.remote.RemoteDataSource
import com.rahmad.moodvies.data.remote.response.review.ReviewItem

class ReviewPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val movieId: Int,
) : PagingSource<Int, ReviewItem>() {

    override fun getRefreshKey(state: PagingState<Int, ReviewItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val result = remoteDataSource.getMovieReviews(movieId, position)
            LoadResult.Page(
                data = result.results,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (result.results.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}