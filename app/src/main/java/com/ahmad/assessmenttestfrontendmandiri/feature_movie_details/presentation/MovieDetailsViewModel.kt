package com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.presentation.MoviesByGenreState
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.presentation.MoviesByGenreViewModel
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.use_case.GetMovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails
) : ViewModel() {

    private val _state = mutableStateOf(MovieDetailsState())
    val state: State<MovieDetailsState> = _state

    private val _eventFlow = MutableSharedFlow<MovieDetailsViewModel.UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onGetMovieDetails(id: Int) {
        viewModelScope.launch {
            getMovieDetails(id = id)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                movieDetails = result.data,
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                movieDetails = null,
                                isLoading = false,
                            )

                            _eventFlow.emit(UIEvent.ShowSnackbar(
                                message = result.message ?: "Unknown Error"
                            ))
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                movieDetails = null,
                                isLoading = true,
                            )
                        }
                    }
                }
                .launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): MovieDetailsViewModel.UIEvent()
    }
}