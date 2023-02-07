package com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.data.repository

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.data.remote.MovieGenresApi
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.model.Genre
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.repository.MovieGenresRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class MovieGenresRepositoryImpl(
    private val api: MovieGenresApi,
): MovieGenresRepository {

    override fun getMovieGenres(): Flow<Resource<List<Genre>>> = flow {
        emit(Resource.Loading())

        try {
            val remoteMovieGenres = api.getMovieGenres()
            val movieGenres = remoteMovieGenres.genres.map { it.toGenre() }
            emit(Resource.Success(movieGenres))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "${e.message}",
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "${e.message}"
            ))
        }
    }
}