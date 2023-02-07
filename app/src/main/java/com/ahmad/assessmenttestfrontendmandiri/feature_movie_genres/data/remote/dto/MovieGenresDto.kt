package com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.data.remote.dto

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.model.MovieGenres

data class MovieGenresDto(
    val genres: List<GenreDto>
) {
    fun toMovieGenres(): MovieGenres {
        return MovieGenres(
            genres = genres.map { it.toGenre() }
        )
    }
}