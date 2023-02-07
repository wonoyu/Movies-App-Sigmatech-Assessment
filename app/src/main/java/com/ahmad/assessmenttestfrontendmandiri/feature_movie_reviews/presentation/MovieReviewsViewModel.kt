package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.presentation.MovieDetailsState
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.presentation.MovieDetailsViewModel
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model.MovieReviews
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.use_case.GetMovieReviews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieReviewsViewModel @Inject constructor(
    private val getMovieReviews: GetMovieReviews
) : ViewModel() {

    private val _state = mutableStateOf(MovieReviewsState())
    val state: State<MovieReviewsState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onGetMovieReviews(id: Int, page: String = "1") {
        viewModelScope.launch {
            getMovieReviews(id = id, page = page)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            Log.d("dsa", "${result.data}")
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
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): MovieReviewsViewModel.UIEvent()
    }
}