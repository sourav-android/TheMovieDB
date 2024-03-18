package com.android.themoviedb.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.android.themoviedb.BuildConfig
import com.android.themoviedb.core.utils.Constants.MOVIE_DATABASE
import com.android.themoviedb.data.api.MovieService
import com.android.themoviedb.data.local.MovieDatabase
import com.android.themoviedb.data.remote.datasource.remote.MovieRemoteDataSource
import com.android.themoviedb.data.remote.datasource.remote.MovieRemoteDataSourceImpl
import com.android.themoviedb.data.repository.paging.MovieRemoteMediator
import com.android.themoviedb.data.repository.MovieRepositoryImpl
import com.android.themoviedb.data.local.MovieEntity
import com.android.themoviedb.data.remote.mapper.MovieDtoMapper
import com.android.themoviedb.data.remote.mapper.MovieEntityMapper
import com.android.themoviedb.domain.repository.MovieRepository
import com.android.themoviedb.domain.usecase.GetMoviesUseCase
import com.android.themoviedb.domain.usecase.GetSavedMoviesUseCase
import com.android.themoviedb.domain.usecase.SaveMoviesUseCase
import com.android.themoviedb.presentation.viewmodel.MovieViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    single {
        OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(MovieService::class.java)
    }

    single { provideDatabase(androidContext()) }
    single { provideDao(get()) }

    single { MovieEntityMapper() }
    single { provideMoviesPager(get(), get(), get()) }

    single<MovieRemoteDataSource> {
        MovieRemoteDataSourceImpl(get())
    }

    single { MovieDtoMapper() }

    single<MovieRepository> {
        MovieRepositoryImpl(get(), get())
    }

    factory { GetMoviesUseCase(get()) }
    factory { SaveMoviesUseCase(get()) }
    factory { GetSavedMoviesUseCase(get()) }

    viewModel {
        MovieViewModel(get() ,get())
    }


}


fun provideDatabase(context: Context) =
    Room.databaseBuilder(
        context, MovieDatabase::class.java,
        MOVIE_DATABASE
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

fun provideDao(db: MovieDatabase) = db.movieDAO()


@OptIn(ExperimentalPagingApi::class)
fun provideMoviesPager(
    movieDatabase: MovieDatabase,
    movieService: MovieService,
    movieEntityMapper: MovieEntityMapper,
): Pager<Int, MovieEntity> {
    return Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = MovieRemoteMediator(
            movieDatabase = movieDatabase,
            movieService = movieService,
            movieEntityMapper = movieEntityMapper
        ),
        pagingSourceFactory = {
            movieDatabase.movieDAO().pagingSource()
        }
    )
}

