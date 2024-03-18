package com.android.themoviedb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.themoviedb.data.remote.datasource.remote.MovieRemoteDataSource
import com.android.themoviedb.data.remote.dto.movie.MovieDto
import com.android.themoviedb.data.remote.mapper.MovieDtoMapper
import com.android.themoviedb.data.repository.paging.MoviePagingSource
import com.android.themoviedb.domain.model.movie.MovieModel
import com.android.themoviedb.domain.repository.MovieRepository
import com.android.themoviedb.domain.util.DomainMapper
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val mapper : MovieDtoMapper
) : MovieRepository {


    override fun getMovieList(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = {
                MoviePagingSource(
                    movieRemoteDataSource = movieRemoteDataSource,
                    movieDtoMapper = mapper
                )
            }
        ).flow
    }


    /*override fun getMovies(): Flow<PagingData<List<MovieDto>>> = flow {
        emit(Resource.Loading())
        val movieResponse = movieService.getMovies()
        if (movieResponse.isSuccessful) {
            movieResponse.body()?.let { movieResponse ->
                movieResponse.result?.map {
                    it.toMovieMapper()

                }
                emit(Resource.Success(movieResponse.result))

            }
        }
    }.flowOn(Dispatchers.IO)
        .catch {
            emit(Resource.Error(it.message.toString()))
        }*/


   /* override suspend fun saveMoviesToDB(movieEntity: List<MovieEntity>) {
        movieDAO.insert(movieEntity)
    }

    override fun getSavedMoviesFromDB(): PagingSource<Int, MovieEntity> =
        movieDAO.getSavedMovies()

    override suspend fun clearAll() {
        movieDAO.clearAll()
    }*/


}