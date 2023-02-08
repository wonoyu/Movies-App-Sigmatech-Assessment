package com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.presentation.MovieDetailsState
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.presentation.MovieDetailsViewModel
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.use_case.GetMovieTrailer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieTrailerViewModel @Inject constructor(
    private val getMovieTrailer: GetMovieTrailer
) : ViewModel() {

    private val _state = mutableStateOf(MovieTrailerState())
    val state: State<MovieTrailerState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onGetMovieTrailer(id: Int) {
        viewModelScope.launch {
            getMovieTrailer(id = id)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                movieTrailer = result.data,
                                isLoading = false,
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                movieTrailer = null,
                                isLoading = false,
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                movieTrailer = null,
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