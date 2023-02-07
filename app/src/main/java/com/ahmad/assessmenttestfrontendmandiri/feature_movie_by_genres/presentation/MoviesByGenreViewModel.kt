package com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.model.MoviesByGenre
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.model.MoviesByGenreData
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.use_case.GetMoviesByGenre
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.presentation.MovieGenresViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesByGenreViewModel @Inject constructor(
    private val getMoviesByGenre: GetMoviesByGenre
) : ViewModel() {

    private val _state = mutableStateOf(MoviesByGenreState())
    val state: State<MoviesByGenreState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onLoadMore(genre: String, page: String) {
        viewModelScope.launch {
            getMoviesByGenre(genre = genre, page = page)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            val remoteMoviesByGenre = result.data
                            val currentMoviesByGenre = state.value.moviesByGenre
                            if (remoteMoviesByGenre != null && currentMoviesByGenre != null) {
                                val currentMovies = currentMoviesByGenre.results.toMutableList()
                                currentMovies.addAll(remoteMoviesByGenre.results)
                                val updatedMoviesByGenre = MoviesByGenre(
                                    page = remoteMoviesByGenre.page,
                                    results = currentMovies,
                                    total_pages = remoteMoviesByGenre.total_pages,
                                    total_results = remoteMoviesByGenre.total_results,
                                )
                                _state.value = state.value.copy(
                                    moviesByGenre = updatedMoviesByGenre,
                                    isLoading = false
                                )
                            }
                        }
                        is Resource.Error -> {
                            _state.value = state.value

                            _eventFlow.emit(UIEvent.ShowSnackbar(
                                result.message ?: "Unknown Error"
                            ))
                        }
                        is Resource.Loading -> {
                            _state.value = state.value
                        }
                    }
                }
                .launchIn(this)
        }
    }

    fun onGetMoviesByGenre(genre: String, page: String = "1") {
        viewModelScope.launch {
            getMoviesByGenre(genre = genre, page = page)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                moviesByGenre = result.data,
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                moviesByGenre = null,
                                isLoading = false,
                            )

                            _eventFlow.emit(UIEvent.ShowSnackbar(
                                result.message ?: "Unknown Error"
                            ))
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                moviesByGenre = null,
                                isLoading = true
                            )
                        }
                    }
                }
                .launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }
}