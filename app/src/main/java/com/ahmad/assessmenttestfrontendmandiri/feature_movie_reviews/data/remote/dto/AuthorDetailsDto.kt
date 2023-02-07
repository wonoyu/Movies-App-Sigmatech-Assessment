package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.data.remote.dto

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model.AuthorDetails

data class AuthorDetailsDto(
    val avatar_path: String?,
    val name: String,
    val rating: Double,
    val username: String
) {
    fun toAuthorDetails(): AuthorDetails {
        return AuthorDetails(
            avatar_path = avatar_path,
            name = name,
            rating = rating,
            username = username,
        )
    }
}