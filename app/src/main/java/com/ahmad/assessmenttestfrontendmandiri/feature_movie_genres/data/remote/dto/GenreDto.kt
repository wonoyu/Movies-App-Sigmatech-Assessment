package com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.data.remote.dto

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.model.Genre

data class GenreDto (
    val id: Int,
    val name: String
) {
    fun toGenre(): Genre {
        return Genre(
            id = id,
            name = name,
        )
    }
}