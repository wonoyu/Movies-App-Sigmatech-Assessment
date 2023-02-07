package com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.presentation

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.model.Genre

data class MovieGenresState(
    val movieGenres: List<Genre> = emptyList(),
    val isLoading: Boolean = false
)