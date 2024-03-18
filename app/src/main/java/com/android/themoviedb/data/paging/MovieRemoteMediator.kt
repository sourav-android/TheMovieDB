package com.android.themoviedb.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.themoviedb.data.api.MovieApi
import com.android.themoviedb.data.local.MovieDatabase
import com.android.themoviedb.domain.model.movie.MovieEntity
import com.android.themoviedb.domain.model.movie.MovieRemoteKeys

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(private val movieApi: MovieApi,
                          private val movieDB: MovieDatabase
) :
    RemoteMediator<Int, MovieEntity>() {

    private val movieDao = movieDB.movieDAO()
    private val movieRemoteKeysDao = movieDB.remoteKeysDAO()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            val response = movieApi.getPopularMovies()
            var endOfPaginationReached = false
            if (response.isSuccessful) {
                val responseData = response.body()
                endOfPaginationReached = responseData == null
                responseData?.let {
                    movieDB.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            movieDao.deleteAllMovies()
                            movieRemoteKeysDao.deleteAllMovieRemoteKeys()
                        }
                        var prevPage: Int?
                        var nextPage: Int

                        responseData.page.let { pageNumber ->
                            nextPage = pageNumber + 1
                            prevPage = if (pageNumber <= 1) null else pageNumber - 1
                        }

                        val keys = responseData.movieEntities.map { movie ->
                            MovieRemoteKeys(
                                id = movie.movieId,
                                prevPage = prevPage,
                                nextPage = nextPage,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }
                        movieRemoteKeysDao.addAllMovieRemoteKeys(movieRemoteKeys = keys)
                        movieDao.addMovies(movieEntities = responseData.movieEntities)
                    }
                }

            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>,
    ): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { id ->
                movieRemoteKeysDao.getMovieRemoteKeys(movieId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieEntity>,
    ): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                movieRemoteKeysDao.getMovieRemoteKeys(movieId = movie.movieId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>,
    ): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movieRemoteKeysDao.getMovieRemoteKeys(movieId = movie.movieId)
            }
    }

}
