package com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.repository

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.model.MoviesByGenre
import kotlinx.coroutines.flow.Flow

interface MoviesByGenreRepository {

    fun getMoviesByGenre(genre: String, page: String): Flow<Resource<MoviesByGenre>>
}