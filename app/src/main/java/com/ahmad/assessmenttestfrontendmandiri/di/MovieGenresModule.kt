package com.ahmad.assessmenttestfrontendmandiri.di

import com.ahmad.assessmenttestfrontendmandiri.core.util.ApiUrls
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.data.remote.MovieGenresApi
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.data.repository.MovieGenresRepositoryImpl
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.repository.MovieGenresRepository
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.use_case.GetMovieGenres
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieGenresModule {

    @Provides
    @Singleton
    fun provideGetMovieGenresUseCase(repository: MovieGenresRepository): GetMovieGenres {
        return GetMovieGenres(repository)
    }

    @Provides
    @Singleton
    fun provideMovieGenresRepository(api: MovieGenresApi): MovieGenresRepository {
        return MovieGenresRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMovieGenresApi(): MovieGenresApi {
        return Retrofit.Builder()
            .baseUrl(ApiUrls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieGenresApi::class.java)
    }
}