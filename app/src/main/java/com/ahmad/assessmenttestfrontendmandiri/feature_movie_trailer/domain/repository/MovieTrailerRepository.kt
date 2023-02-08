package com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.repository

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.model.MovieTrailer
import kotlinx.coroutines.flow.Flow

interface MovieTrailerRepository {

    fun getMovieTrailer(id: Int): Flow<Resource<MovieTrailer>>
}