package com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.presentation

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.model.MovieTrailer

data class MovieTrailerState(
    val movieTrailer: MovieTrailer? = null,
    val isLoading: Boolean = false,
)
