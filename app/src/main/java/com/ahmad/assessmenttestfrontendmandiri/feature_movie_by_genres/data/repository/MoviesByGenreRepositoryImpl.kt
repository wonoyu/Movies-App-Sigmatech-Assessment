package com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.data.repository

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.data.remote.MoviesByGenreApi
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.model.MoviesByGenre
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.repository.MoviesByGenreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MoviesByGenreRepositoryImpl(
    private val api: MoviesByGenreApi
): MoviesByGenreRepository {

    override fun getMoviesByGenre(genre: String, page: String): Flow<Resource<MoviesByGenre>> = flow {
        emit(Resource.Loading())

        try {
            val remoteMoviesByGenre = api.getMoviesByGenre(genre = genre, page = page)
            val moviesByGenre = remoteMoviesByGenre.toMoviesByGenre()
            emit(Resource.Success(moviesByGenre))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "${e.message}",
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "${e.message}",
            ))
        }
    }
}