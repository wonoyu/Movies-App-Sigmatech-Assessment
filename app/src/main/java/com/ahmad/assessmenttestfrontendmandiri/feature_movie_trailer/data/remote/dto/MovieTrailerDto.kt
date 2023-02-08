package com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.data.remote.dto

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.model.MovieTrailer

data class MovieTrailerDto(
    val id: Int,
    val results: List<MovieTrailerDataDto>
) {
    fun toMovieTrailer(): MovieTrailer {
        return MovieTrailer(
            id = id,
            results = results.map { it.toMovieTrailerData() }
        )
    }
}