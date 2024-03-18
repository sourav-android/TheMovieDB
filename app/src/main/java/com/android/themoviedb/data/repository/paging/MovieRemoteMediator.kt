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
import com.android.themoviedb.data.remote.mapper.MovieEntityMapper
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieDatabase: MovieDatabase,
    private val movieService: MovieService,
    private val movieEntityMapper: MovieEntityMapper
)  : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>,
    ): MediatorResult {
        return try {
            return try {
                val loadKey = when(loadType) {
                    LoadType.REFRESH -> 1
                    LoadType.PREPEND -> return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    LoadType.APPEND -> {
                        val lastItem = state.lastItemOrNull()
                        if(lastItem == null) {
                            1
                        } else {
                            (lastItem.id / state.config.pageSize) + 1
                        }
                    }
                }

                val movieResponse = movieService.getMovies(
                    apiKey = BuildConfig.TMDB_KEY,
                    pageNumber = loadKey,)

                movieDatabase.withTransaction {
                    if(loadType == LoadType.REFRESH) {
                        movieDatabase.movieDAO().clearAll()
                    }
                    val movieEntities = movieResponse.results?: emptyList()
                    val mappedMovies = movieEntityMapper.fromEntityList(movieEntities)
                    movieDatabase.movieDAO().upsertAll(mappedMovies)
                    Log.d("MappedMovies", "MoviesToDB : $mappedMovies")
                }

                MediatorResult.Success(
                    endOfPaginationReached = movieResponse.results!!.isEmpty()
                )
            } catch(e: IOException) {
                MediatorResult.Error(e)
            } catch(e: HttpException) {
                MediatorResult.Error(e)
            }
        }catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}