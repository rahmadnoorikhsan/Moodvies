package com.rahmad.moodvies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.rahmad.moodvies.data.paging.MoviePagingSource
import com.rahmad.moodvies.data.paging.ReviewPagingSource
import com.rahmad.moodvies.data.remote.RemoteDataSource
import com.rahmad.moodvies.domain.repository.MovieRepository
import com.rahmad.moodvies.utils.DataMapper
import com.rahmad.moodvies.utils.DataMapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val remote: RemoteDataSource
) : MovieRepository {

    override fun getPopularMovies() = flow {
        emit(Result.Loading())
        try {
            val response = remote.getPopularMovies()
            val movies = response.results.map { movieItem ->
                movieItem.toDomain()
            }
            val data = coroutineScope {
                movies.map { movie ->
                    async {
                        movie.logo = remote.getImageMovie(movie.id).logos.map { it.toDomain() }
                            .firstOrNull()?.filePath
                        movie
                    }
                }.awaitAll()
            }
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getAllGenres() = flow {
        emit(Result.Loading())
        try {
            val response = remote.getAllGenres()
            val data = response.genres.map { genreItem ->
                genreItem.toDomain()
            }
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMoviesByGenre(genresId: String) = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            MoviePagingSource(remote, genresId)
        }
    ).flow.map {
        DataMapper.mapMoviePagingResponseToMoviePagingDomain(it)
    }

    override fun getMovieDetails(movieId: Int) = flow {
        emit(Result.Loading())
        try {
            val response = remote.getMovieDetails(movieId)
            val data = response.toDomain()
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    override fun getMovieTrailers(movieId: Int) = flow {
        emit(Result.Loading())
        try {
            val response = remote.getMovieTrailers(movieId)
            val data = response.results.map { trailerItem ->
                trailerItem.toDomain()
            }
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    override fun getMovieReviews(movieId: Int) = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            ReviewPagingSource(remote, movieId)
        }
    ).flow.map {
        DataMapper.mapReviewPagingResponseToReviewPagingDomain(it)
    }
}