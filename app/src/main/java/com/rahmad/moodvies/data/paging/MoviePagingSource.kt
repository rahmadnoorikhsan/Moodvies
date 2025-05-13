package com.rahmad.moodvies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rahmad.moodvies.data.remote.RemoteDataSource
import com.rahmad.moodvies.data.remote.response.movies.MovieItem

class MoviePagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val genresId: String,
) : PagingSource<Int, MovieItem>() {

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val result = remoteDataSource.getMoviesByGenre(genresId = genresId, page = position)
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