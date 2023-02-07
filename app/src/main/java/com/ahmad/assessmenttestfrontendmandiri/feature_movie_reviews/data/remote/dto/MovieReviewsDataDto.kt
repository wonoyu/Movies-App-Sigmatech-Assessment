package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.data.remote.dto

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model.MovieReviewsData

data class MovieReviewsDataDto(
    val author: String,
    val author_details: AuthorDetailsDto,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val url: String
) {
    fun toMovieReviewsData(): MovieReviewsData {
        return MovieReviewsData(
            author = author,
            author_details = author_details.toAuthorDetails(),
            content = content,
            created_at = created_at,
            id = id,
            updated_at = updated_at,
            url = url,
        )
    }
}