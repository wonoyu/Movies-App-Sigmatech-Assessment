package com.ahmad.assessmenttestfrontendmandiri.di

import com.ahmad.assessmenttestfrontendmandiri.core.util.ApiUrls
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.data.remote.MoviesByGenreApi
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.data.repository.MoviesByGenreRepositoryImpl
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.repository.MoviesByGenreRepository
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.use_case.GetMoviesByGenre
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesByGenreModule {

    @Provides
    @Singleton
    fun provideGetMoviesByGenreUseCase(repository: MoviesByGenreRepository): GetMoviesByGenre {
        return GetMoviesByGenre(repository)
    }

    @Provides
    @Singleton
    fun provideMoviesByGenreRepository(api: MoviesByGenreApi): MoviesByGenreRepository {
        return MoviesByGenreRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMoviesByGenreApi(): MoviesByGenreApi {
        return Retrofit.Builder()
            .baseUrl(ApiUrls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesByGenreApi::class.java)
    }
}