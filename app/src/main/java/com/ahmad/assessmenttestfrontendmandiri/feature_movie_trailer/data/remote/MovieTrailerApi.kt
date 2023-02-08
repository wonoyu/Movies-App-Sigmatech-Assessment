package com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.data.remote

import com.ahmad.assessmenttestfrontendmandiri.core.util.ApiUrls
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.data.remote.dto.MovieTrailerDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieTrailerApi {

    @GET(ApiUrls.MOVIE_TRAILER)
    suspend fun getMovieTrailer(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = ApiUrls.API_KEY,
        @Query("language") language: String = "en-US",
    ): MovieTrailerDto
}