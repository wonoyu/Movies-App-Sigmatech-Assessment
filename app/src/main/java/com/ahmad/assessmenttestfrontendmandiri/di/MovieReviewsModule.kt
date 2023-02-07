package com.ahmad.assessmenttestfrontendmandiri.di

import com.ahmad.assessmenttestfrontendmandiri.core.util.ApiUrls
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.data.remote.MovieReviewsApi
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.data.repository.MovieReviewsRepositoryImpl
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.repository.MovieReviewsRepository
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.use_case.GetMovieReviews
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
object MovieReviewsModule {

    @Provides
    @Singleton
    fun provideGetMovieReviewsUseCase(repository: MovieReviewsRepository): GetMovieReviews {
        return GetMovieReviews(repository)
    }

    @Provides
    @Singleton
    fun provideMovieReviewsRepository(api: MovieReviewsApi): MovieReviewsRepository {
        return MovieReviewsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMovieReviewsApi(): MovieReviewsApi {
        return Retrofit.Builder()
            .baseUrl(ApiUrls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieReviewsApi::class.java)
    }
}