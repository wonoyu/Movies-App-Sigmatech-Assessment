package com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.presentation

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.model.MoviesByGenre

data class MoviesByGenreState(
    val moviesByGenre: MoviesByGenre? = null,
    val isLoading: Boolean = false
)