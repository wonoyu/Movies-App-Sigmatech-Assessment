package com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.data.repository

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.data.remote.MovieDetailsApi
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.model.MovieDetails
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MovieDetailsRepositoryImpl(
    private val api: MovieDetailsApi
): MovieDetailsRepository {

    override fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>> = flow {
        emit(Resource.Loading())

        try {
            val movieDetailsDto = api.getMovieDetails(id)
            val movieDetailsEntity = movieDetailsDto.toMoviesDetail()
            emit(Resource.Success(movieDetailsEntity))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "${e.message}"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "${e.message}"))
        }
    }
}