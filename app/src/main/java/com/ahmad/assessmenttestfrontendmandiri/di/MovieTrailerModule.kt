package com.ahmad.assessmenttestfrontendmandiri.di

import com.ahmad.assessmenttestfrontendmandiri.core.util.ApiUrls
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.data.remote.MovieTrailerApi
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.data.repository.MovieTrailerRepositoryImpl
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.repository.MovieTrailerRepository
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.use_case.GetMovieTrailer
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
object MovieTrailerModule {

    @Provides
    @Singleton
    fun provideMovieTrailerUseCase(repository: MovieTrailerRepository): GetMovieTrailer {
        return GetMovieTrailer(repository)
    }

    @Provides
    @Singleton
    fun provideMovieTrailerRepository(api: MovieTrailerApi): MovieTrailerRepository {
        return MovieTrailerRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMovieTrailerApi(): MovieTrailerApi {
        return Retrofit.Builder()
            .baseUrl(ApiUrls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieTrailerApi::class.java)
    }
}