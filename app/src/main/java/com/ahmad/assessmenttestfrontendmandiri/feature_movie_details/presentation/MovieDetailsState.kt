package com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.presentation

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.model.MovieDetails

data class MovieDetailsState(
    val movieDetails: MovieDetails? = null,
    val isLoading: Boolean = false,
)