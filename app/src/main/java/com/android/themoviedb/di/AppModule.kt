package com.android.themoviedb.di

import android.content.Context
import androidx.room.Room
import com.android.themoviedb.BuildConfig
import com.android.themoviedb.core.utils.Constants.MOVIE_DATABASE
import com.android.themoviedb.data.api.MovieApi
import com.android.themoviedb.data.local.MovieDatabase
import com.android.themoviedb.data.repository.MovieRepositoryImpl
import com.android.themoviedb.data.repository.datasource.remote.MovieLocalDataSource
import com.android.themoviedb.data.repository.datasource.remote.MovieRemoteDataSource
import com.android.themoviedb.data.repository.datasourceImpl.MovieLocalDataSourceImpl
import com.android.themoviedb.data.repository.datasourceImpl.MovieRemoteDataSourceImpl
import com.android.themoviedb.domain.repository.MovieRepository
import com.android.themoviedb.domain.usecase.GetMoviesFromDBUseCase
import com.android.themoviedb.domain.usecase.GetPopularMoviesUseCase
import com.android.themoviedb.domain.usecase.MovieUseCases
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
        get<Retrofit>().create(MovieApi::class.java)
    }

    single { provideDatabase(androidContext()) }
    single { provideMovieDao(get()) }
    single { provideRemoteKeysDao(get()) }



    single<MovieRemoteDataSource> {
        MovieRemoteDataSourceImpl(get(), get())
    }

    single<MovieLocalDataSource> {
        MovieLocalDataSourceImpl(get())
    }

    single<MovieRepository> {
        MovieRepositoryImpl(get(), get())
    }

    factory { GetPopularMoviesUseCase(get()) }
    factory { GetMoviesFromDBUseCase(get()) }
    factory { MovieUseCases(get(), get()) }

    viewModel {
        MovieViewModel(get())
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

fun provideMovieDao(db: MovieDatabase) = db.movieDAO()
fun provideRemoteKeysDao(db: MovieDatabase) = db.remoteKeysDAO()




