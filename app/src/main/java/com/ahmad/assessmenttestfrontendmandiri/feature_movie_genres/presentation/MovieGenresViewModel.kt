package com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.use_case.GetMovieGenres
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieGenresViewModel @Inject constructor(
    private val getMovieGenres: GetMovieGenres
) : ViewModel() {

    private val _state = mutableStateOf(MovieGenresState())
    val state: State<MovieGenresState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            getMovieGenres()
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                movieGenres = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                movieGenres = emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(UIEvent.ShowSnackbar(
                                result.message ?: "Unknown Error"
                            ))
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                movieGenres = emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }
                .launchIn(this)
        }
    }

    fun onGetMovieGenres() {
        viewModelScope.launch {
            getMovieGenres()
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                movieGenres = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                movieGenres = emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(UIEvent.ShowSnackbar(
                                result.message ?: "Unknown Error"
                            ))
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                movieGenres = emptyList(),
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