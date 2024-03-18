package com.android.themoviedb.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.themoviedb.BuildConfig
import com.android.themoviedb.data.remote.datasource.remote.MovieRemoteDataSource
import com.android.themoviedb.data.remote.mapper.MovieDtoMapper
import com.android.themoviedb.domain.model.movie.MovieModel
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieDtoMapper: MovieDtoMapper
) : PagingSource<Int, MovieModel>() {

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, MovieModel> {
        return try {
            val currentPage = params.key ?: 1
            val movieResponse  = movieRemoteDataSource.getMovies(
                apiKey = BuildConfig.TMDB_KEY,
                pageNumber = currentPage
            )

            val movies = movieResponse.results ?: emptyList()
            val mappedMovies = movieDtoMapper.toDomainList(movies)

            LoadResult.Page(
                data = mappedMovies,
                prevKey = if (currentPage == 1) null else currentPage.minus(1),
                nextKey = if (movies.isEmpty()) null else movieResponse.page?.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}
