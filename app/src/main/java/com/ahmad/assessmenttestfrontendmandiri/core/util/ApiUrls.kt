package com.ahmad.assessmenttestfrontendmandiri.core.util

class ApiUrls {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "7700ab81ba97abbcce035822b82db391"
        const val GENRES = "genre/movie/list"
        const val MOVIES_BY_GENRE = "discover/movie"
        const val MOVIE_DETAILS = "movie/{id}"
        const val MOVIE_REVIEWS = "movie/{id}/reviews"
        const val MOVIE_TRAILER = "movie/{id}/videos"
    }
}