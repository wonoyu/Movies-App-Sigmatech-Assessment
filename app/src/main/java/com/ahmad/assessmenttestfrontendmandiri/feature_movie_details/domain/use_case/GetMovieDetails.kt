package com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.use_case

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.model.MovieDetails
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow

class GetMovieDetails(
    private val repository: MovieDetailsRepository
) {

    operator fun invoke(id: Int): Flow<Resource<MovieDetails>> {
        return repository.getMovieDetails(id)
    }
}