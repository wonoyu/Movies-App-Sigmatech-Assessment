package com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.repository

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface MovieGenresRepository {

    fun getMovieGenres(): Flow<Resource<List<Genre>>>
}