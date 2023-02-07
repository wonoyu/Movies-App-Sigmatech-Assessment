package com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.data.remote.dto

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.model.MoviesByGenre

data class MoviesByGenreDto(
    val page: Int,
    val results: List<MoviesByGenreDataDto>,
    val total_pages: Int,
    val total_results: Int
) {
    fun toMoviesByGenre(): MoviesByGenre {
        return MoviesByGenre(
            page = page,
            results = results.map { it.toMoviesByGenreData() },
            total_pages = total_pages,
            total_results = total_results,
        )
    }
}