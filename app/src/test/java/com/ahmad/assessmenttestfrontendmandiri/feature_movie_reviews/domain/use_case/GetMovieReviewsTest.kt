package com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.use_case

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model.MovieReviews
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.repository.MovieReviewsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetMovieReviewsTest {
    private lateinit var getMovieReviews: GetMovieReviews

    @MockK
    private lateinit var fakeRepository: MovieReviewsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getMovieReviews = GetMovieReviews(fakeRepository)
    }

    @Test
    fun `emits ResourceError when failed to call http request`() = runBlocking {
        coEvery { fakeRepository.getMovieReviews(1, "1") } returns flow {
            emit(Resource.Error("Http Error"))
        }
        getMovieReviews(1, "1").onEach { result ->
            assert(result is Resource.Error)
        }.launchIn(this)
        coVerify { fakeRepository.getMovieReviews(1, "1") }
    }

    @Test
    fun `emits ResourceError when device has no internet connection`() = runBlocking {
        coEvery { fakeRepository.getMovieReviews(1, "1") } returns flow {
            emit(Resource.Error("No Internet Connection"))
        }
        getMovieReviews(1, "1").onEach { result ->
            assert(result is Resource.Error)
        }.launchIn(this)
        coVerify { fakeRepository.getMovieReviews(1, "1") }
    }

    @Test
    fun `emits ResourceSuccess when retrofit request is successful`() = runBlocking {
        val dummyData = MovieReviews(
            id = 1,
            page = 1,
            results = emptyList(),
            total_pages = 1,
            total_results = 1,
        )
        coEvery { fakeRepository.getMovieReviews(1, "1") } returns flow {
            emit(Resource.Success(dummyData))
        }
        getMovieReviews(1, "1").onEach { result ->
            assert(result is Resource.Success)
        }.launchIn(this)
        coVerify { fakeRepository.getMovieReviews(1, "1") }
    }
}