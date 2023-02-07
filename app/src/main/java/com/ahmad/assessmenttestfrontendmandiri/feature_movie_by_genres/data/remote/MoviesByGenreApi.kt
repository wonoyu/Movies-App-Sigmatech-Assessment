package com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.data.remote

import com.ahmad.assessmenttestfrontendmandiri.core.util.ApiUrls
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.data.remote.dto.MoviesByGenreDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesByGenreApi {

    @GET(ApiUrls.MOVIES_BY_GENRE)
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String = ApiUrls.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: String = "1",
        @Query("with_genres") genre: String = "12"
    ): MoviesByGenreDto

}