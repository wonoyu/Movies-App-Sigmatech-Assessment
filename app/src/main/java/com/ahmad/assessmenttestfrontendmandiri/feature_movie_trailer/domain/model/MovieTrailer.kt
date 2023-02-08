package com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.model

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.data.remote.dto.MovieTrailerDataDto

data class MovieTrailer(
    val id: Int,
    val results: List<MovieTrailerData>
)
