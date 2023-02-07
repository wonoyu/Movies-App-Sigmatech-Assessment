package com.ahmad.assessmenttestfrontendmandiri.di

import com.ahmad.assessmenttestfrontendmandiri.core.util.ApiUrls
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.data.remote.MovieDetailsApi
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.data.repository.MovieDetailsRepositoryImpl
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.repository.MovieDetailsRepository
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.use_case.GetMovieDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDetailsModule {

    @Provides
    @Singleton
    fun provideGetMovieDetailsUseCase(repository: MovieDetailsRepository): GetMovieDetails {
        return GetMovieDetails(repository)
    }

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(api: MovieDetailsApi): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMovieDetailsApi(): MovieDetailsApi {
        return Retrofit.Builder()
            .baseUrl(ApiUrls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDetailsApi::class.java)
    }
}