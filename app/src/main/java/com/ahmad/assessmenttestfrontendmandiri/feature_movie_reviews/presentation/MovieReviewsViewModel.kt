package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.model.MoviesByGenre
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.presentation.MoviesByGenreViewModel
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.presentation.MovieDetailsState
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.presentation.MovieDetailsViewModel
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model.MovieReviews
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.use_case.GetMovieReviews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieReviewsViewModel @Inject constructor(
    private val getMovieReviews: GetMovieReviews
) : ViewModel() {

    private val _state = mutableStateOf(MovieReviewsState())
    val state: State<MovieReviewsState> = _state

    private val _selectedReviewIndex = mutableStateOf(0)
    val selectedReviewIndex: State<Int> = _selectedReviewIndex

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onSelectReview(index: Int) {
        viewModelScope.launch {
            _selectedReviewIndex.value = index
        }
    }

    fun onLoadMore(id: Int, page: String) {
        viewModelScope.launch {
            getMovieReviews(id = id, page = page)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            val remoteMovieReviews = result.data
                            val currentMovieReviews = state.value.movieReviews
                            if (remoteMovieReviews != null && currentMovieReviews != null) {
                                val currentReviews = currentMovieReviews.results.toMutableList()
                                currentReviews.addAll(remoteMovieReviews.results)
                                val updatedMovieReviews = MovieReviews(
                                    id = remoteMovieReviews.id,
                                    page = remoteMovieReviews.page,
                                    results = currentReviews,
                                    total_pages = remoteMovieReviews.total_pages,
                                    total_results = remoteMovieReviews.total_results,
                                )
                                _state.value = state.value.copy(
                                    movieReviews = updatedMovieReviews,
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

    fun onGetMovieReviews(id: Int, page: String = "1") {
        viewModelScope.launch {
            getMovieReviews(id = id, page = page)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                movieReviews = result.data,
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                movieReviews = null,
                                isLoading = false,
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                movieReviews = null,
                                isLoading = true
                            )
                        }
                    }
                }
                .launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): MovieReviewsViewModel.UIEvent()
    }
}