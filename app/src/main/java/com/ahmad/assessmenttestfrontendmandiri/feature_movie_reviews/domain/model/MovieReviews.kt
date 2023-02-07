package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model

data class MovieReviews(
    val id: Int,
    val page: Int,
    val results: List<MovieReviewsData>,
    val total_pages: Int,
    val total_results: Int
)
