package com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.model

data class MoviesByGenre(
    val page: Int,
    val results: List<MoviesByGenreData>,
    val total_pages: Int,
    val total_results: Int
)
