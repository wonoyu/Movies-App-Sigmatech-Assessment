package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.data.remote

import com.ahmad.assessmenttestfrontendmandiri.core.util.ApiUrls
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.data.remote.dto.MovieReviewsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieReviewsApi {

    @GET(ApiUrls.MOVIE_REVIEWS)
    suspend fun getMovieReviewsApi(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = ApiUrls.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: String = "1",
    ): MovieReviewsDto
}