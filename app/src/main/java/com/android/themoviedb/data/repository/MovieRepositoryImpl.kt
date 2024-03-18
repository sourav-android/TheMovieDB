package com.android.themoviedb.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.themoviedb.data.api.MovieService
import com.android.themoviedb.data.local.MovieDatabase
import com.android.themoviedb.data.local.MovieEntity
import com.android.themoviedb.data.remote.datasource.remote.MovieRemoteDataSource
import com.android.themoviedb.data.remote.mapper.MovieEntityMapper
import com.android.themoviedb.data.repository.paging.MovieRemoteMediator
import com.android.themoviedb.pagingonly.MovieDtoMapper
import com.android.themoviedb.pagingonly.MoviePagingSource
import com.android.themoviedb.pagingonly.MovieModel
import com.android.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val mapper: MovieDtoMapper,
    private val movieService: MovieService,
    private val movieDatabase: MovieDatabase,
    private val movieEntityMapper: MovieEntityMapper,
) : MovieRepository {


    @OptIn(ExperimentalPagingApi::class)
    override fun getMoviesFromLocal(): Flow<PagingData<MovieEntity>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100
        ),
        remoteMediator = MovieRemoteMediator(movieDatabase, movieService, movieEntityMapper),
        pagingSourceFactory = { movieDatabase.movieDAO().getMovies() }
    ).flow

    override fun getMovieList(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
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