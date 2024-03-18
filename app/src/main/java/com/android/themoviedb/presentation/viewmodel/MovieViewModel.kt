package com.android.themoviedb.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.android.themoviedb.data.local.MovieEntity
import com.android.themoviedb.data.remote.mapper.MovieDtoMapper
import com.android.themoviedb.domain.model.movie.MovieModel
import com.android.themoviedb.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    pager: Pager<Int, MovieEntity>
) : ViewModel() {

    private val _moviesState: MutableStateFlow<PagingData<MovieModel>> =
        MutableStateFlow(value = PagingData.empty())
    val moviesState: MutableStateFlow<PagingData<MovieModel>> get() = _moviesState

    init {
        onEvent(HomeEvent.MovieScreen)
    }

    private fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.MovieScreen -> {
                    getMovies()
                }
            }
        }
    }

    private suspend fun getMovies() {
        getMoviesUseCase.invoke()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _moviesState.value = it
                /*it.map { movieDto ->
                    saveMovies(movieDto.toMovieMapper())
                    Log.i("MovieResponse", "LocalDB : ${movieDto.toMovieMapper()}")
                }*/
            }

    }

    val moviePagingFlow = pager
        .flow
        .map { pagingData ->
            pagingData.map { it }
        }
        .cachedIn(viewModelScope)

    /*private fun saveMovies(movieEntity: PagingData<MovieEntity>) {
        viewModelScope.launch {
            saveMoviesUseCase.invoke(movieEntity)
        }
    }

    fun getSavedArticles() = liveData {
        getSavedMoviesUseCase.invoke().collect {
            emit(it)
        }
    }*/

    /*

     init {
        fetchMoviesFromRemote()
    }


    private fun fetchMoviesFromRemote() = getMoviesUseCase()?.onEach { response ->
        when (response) {
            is Resource.Error -> {
                _movieState.value = UiState().copy(errorMsg = response.msg)
                _uiEffect.emit(UiEffect.ShowSnackBar(response.msg.toString()))
                Log.i("MoviesResponse", "Error : ${response.msg}")
            }

            is Resource.Loading -> {
                _movieState.value = UiState().copy(isLoading = true)

            }

            is Resource.Success -> {
                _movieState.value = UiState().copy(movies = response.data)
                Log.i("MoviesResponse", "RemoteResponse : ${response.data}")

                delay(300)
                splashCondition = false

                val saveMovies = _movieState.value.movies?.forEach { moviesDto ->
                    saveMovies(moviesDto.toMovieMapper())
                }
                Log.i("MoviesResponse", "SaveDataToLocal : $saveMovies")
            }
        }
    }?.launchIn(viewModelScope)

    */

}

sealed class HomeEvent {
    data object MovieScreen : HomeEvent()
}