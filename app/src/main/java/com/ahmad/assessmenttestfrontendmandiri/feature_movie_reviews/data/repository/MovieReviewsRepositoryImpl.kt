package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.data.repository

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.data.remote.MovieReviewsApi
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model.MovieReviews
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.repository.MovieReviewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MovieReviewsRepositoryImpl(
    private val api: MovieReviewsApi
): MovieReviewsRepository {

    override fun getMovieReviews(id: Int, page: String): Flow<Resource<MovieReviews>> = flow {
        emit(Resource.Loading())

        try {
            val movieReviewsDto = api.getMovieReviewsApi(id = id, page = page)
            val movieReviews = movieReviewsDto.toMovieReviews()
            emit(Resource.Success(movieReviews))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "${e.message}"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "${e.message}"))
        }
    }
}