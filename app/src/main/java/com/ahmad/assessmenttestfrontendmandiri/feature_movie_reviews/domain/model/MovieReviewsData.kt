package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.data.remote.dto.AuthorDetailsDto

data class MovieReviewsData(
    val author: String,
    val author_details: AuthorDetails,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val url: String
)
