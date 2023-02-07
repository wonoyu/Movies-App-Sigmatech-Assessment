package com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.data.remote

import com.ahmad.assessmenttestfrontendmandiri.core.util.ApiUrls
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.data.remote.dto.GenreDto
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.data.remote.dto.MovieGenresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieGenresApi {

    @GET(ApiUrls.GENRES)
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = ApiUrls.API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieGenresDto

}