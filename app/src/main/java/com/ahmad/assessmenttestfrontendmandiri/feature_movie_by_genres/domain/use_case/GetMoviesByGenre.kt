package com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.use_case

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.model.MoviesByGenre
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.repository.MoviesByGenreRepository
import kotlinx.coroutines.flow.Flow

class GetMoviesByGenre(
    private val repository: MoviesByGenreRepository
) {

    operator fun invoke(genre: String, page: String): Flow<Resource<MoviesByGenre>> {
        return repository.getMoviesByGenre(genre = genre, page = page)
    }
}