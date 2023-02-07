package com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.data.remote

import com.ahmad.assessmenttestfrontendmandiri.core.util.ApiUrls
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.data.remote.dto.MovieDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsApi {

    @GET(ApiUrls.MOVIE_DETAILS)
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = ApiUrls.API_KEY,
        @Query("language") language: String = "en-US",
    ): MovieDetailsDto
}