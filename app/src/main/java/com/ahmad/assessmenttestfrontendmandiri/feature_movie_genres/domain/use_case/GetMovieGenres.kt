package com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.use_case

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.model.Genre
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.repository.MovieGenresRepository
import kotlinx.coroutines.flow.Flow

class GetMovieGenres(
    private val repository: MovieGenresRepository
) {

    operator fun invoke(): Flow<Resource<List<Genre>>> {
        return repository.getMovieGenres()
    }
}