package com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.repository

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>>
}