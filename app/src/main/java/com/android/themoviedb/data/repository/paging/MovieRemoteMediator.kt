package com.android.themoviedb.data.repository.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.themoviedb.BuildConfig
import com.android.themoviedb.data.api.MovieService
import com.android.themoviedb.data.local.MovieDatabase
import com.android.themoviedb.data.local.MovieEntity
import com.android.themoviedb.data.local.MovieRemoteKeys
import com.android.themoviedb.data.remote.mapper.MovieEntityMapper
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieDatabase: MovieDatabase,
    private val movieService: MovieService,
    private val movieEntityMapper: MovieEntityMapper,
) : RemoteMediator<Int, MovieEntity>() {

    private val movieDao = movieDatabase.movieDAO()
    private val movieRemoteKeysDao = movieDatabase.remoteKeysDAO()
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>,
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
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

            val movieResponse = movieService.getPopularMovies(
                apiKey = BuildConfig.TMDB_KEY,
                pageNumber = currentPage + 1
            )
            Log.i("CurrentPage", "pageNumber = $currentPage")
            val endOfPaginationReached = movieResponse.totalPages == currentPage

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.deleteMovies()
                    movieRemoteKeysDao.deleteAllRemoteKeys()
                }

                val movieEntities = movieResponse.results ?: emptyList()
                val mappedMovies = movieEntityMapper.fromEntityList(movieEntities)
                movieDao.addMovies(mappedMovies)


                val keys = movieEntities.map { movie ->
                    MovieRemoteKeys(
                        id = movie.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                movieDatabase.remoteKeysDAO().addAllRemoteKeys(keys)
            }

            MediatorResult.Success(endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>,
    ): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieRemoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieEntity>,
    ): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                movieRemoteKeysDao.getRemoteKeys(id = movie.id)
            }
    }


    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>,
    ): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movieRemoteKeysDao.getRemoteKeys(id = movie.id)
            }
    }
}
