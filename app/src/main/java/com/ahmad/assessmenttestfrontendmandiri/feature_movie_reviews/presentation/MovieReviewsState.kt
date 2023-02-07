package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.presentation

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model.MovieReviews

data class MovieReviewsState(
    val movieReviews: MovieReviews? = null,
    val isLoading: Boolean = false,
)