package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.use_case

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model.MovieReviews
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.repository.MovieReviewsRepository
import kotlinx.coroutines.flow.Flow

class GetMovieReviews(
    private val repository: MovieReviewsRepository
) {

    operator fun invoke(id: Int, page: String = "1"): Flow<Resource<MovieReviews>> {
        return repository.getMovieReviews(id = id, page = page)
    }
}