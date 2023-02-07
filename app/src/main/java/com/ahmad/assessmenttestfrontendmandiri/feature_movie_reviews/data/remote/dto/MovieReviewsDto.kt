package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.data.remote.dto

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model.MovieReviews

data class MovieReviewsDto(
    val id: Int,
    val page: Int,
    val results: List<MovieReviewsDataDto>,
    val total_pages: Int,
    val total_results: Int
) {
    fun toMovieReviews(): MovieReviews {
        return MovieReviews(
            id = id,
            page = page,
            results = results.map { it.toMovieReviewsData() },
            total_pages = total_pages,
            total_results = total_results,
        )
    }
}