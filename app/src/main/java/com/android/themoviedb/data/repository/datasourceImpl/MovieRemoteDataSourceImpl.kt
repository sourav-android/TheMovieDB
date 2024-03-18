package com.android.themoviedb.data.repository.datasourceImpl


import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.themoviedb.data.api.MovieApi
import com.android.themoviedb.data.local.MovieDatabase
import com.android.themoviedb.data.paging.MovieRemoteMediator
import com.android.themoviedb.data.repository.datasource.remote.MovieRemoteDataSource
import com.android.themoviedb.domain.model.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

class MovieRemoteDataSourceImpl(
    private val movieApi: MovieApi,
    private val movieDB: MovieDatabase
) : MovieRemoteDataSource {

    private val movieDao = movieDB.movieDAO()

    override  fun getPopularMovies() : Flow<PagingData<MovieEntity>> {
        val pagingSourceFactory = { movieDao.getAllMovies() }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = MovieRemoteMediator(
                movieApi,
                movieDB
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }


}