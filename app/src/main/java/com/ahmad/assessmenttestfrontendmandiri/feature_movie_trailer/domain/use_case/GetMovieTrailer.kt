package com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.use_case

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.model.MovieTrailer
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.repository.MovieTrailerRepository
import kotlinx.coroutines.flow.Flow

class GetMovieTrailer(
    private val repository: MovieTrailerRepository
) {

    operator fun invoke(id: Int): Flow<Resource<MovieTrailer>> {
        return repository.getMovieTrailer(id = id)
    }
}