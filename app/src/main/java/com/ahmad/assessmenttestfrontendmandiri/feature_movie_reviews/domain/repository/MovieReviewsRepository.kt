package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.repository

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model.MovieReviews
import kotlinx.coroutines.flow.Flow

interface MovieReviewsRepository {

    fun getMovieReviews(id: Int, page: String = "1"): Flow<Resource<MovieReviews>>
}