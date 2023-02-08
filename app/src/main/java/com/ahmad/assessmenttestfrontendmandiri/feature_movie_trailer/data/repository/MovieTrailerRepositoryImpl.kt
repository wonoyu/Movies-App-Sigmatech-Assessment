package com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.data.repository

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.data.remote.MovieTrailerApi
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.model.MovieTrailer
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.repository.MovieTrailerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MovieTrailerRepositoryImpl(
    private val api: MovieTrailerApi
): MovieTrailerRepository {

    override fun getMovieTrailer(id: Int): Flow<Resource<MovieTrailer>> = flow {
        emit(Resource.Loading())

        try {
            val movieTrailerDto = api.getMovieTrailer(id = id)
            val movieTrailer = movieTrailerDto.toMovieTrailer()
            emit(Resource.Success(movieTrailer))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "${e.message}"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "${e.message}"))
        }
    }
}