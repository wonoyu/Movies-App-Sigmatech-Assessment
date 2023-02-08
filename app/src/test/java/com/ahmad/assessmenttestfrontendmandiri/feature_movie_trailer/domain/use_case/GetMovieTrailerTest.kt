package com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.use_case

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.model.MovieReviews
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.repository.MovieReviewsRepository
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_reviews.domain.use_case.GetMovieReviews
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.model.MovieTrailer
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_trailer.domain.repository.MovieTrailerRepository
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

class GetMovieTrailerTest {
    private lateinit var getMovieTrailer: GetMovieTrailer

    @MockK
    private lateinit var fakeRepository: MovieTrailerRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getMovieTrailer = GetMovieTrailer(fakeRepository)
    }

    @Test
    fun `emits ResourceError when failed to call http request`() = runBlocking {
        coEvery { fakeRepository.getMovieTrailer(1) } returns flow {
            emit(Resource.Error("Http Error"))
        }
        getMovieTrailer(1).onEach { result ->
            assert(result is Resource.Error)
        }.launchIn(this)
        coVerify { fakeRepository.getMovieTrailer(1) }
    }

    @Test
    fun `emits ResourceError when device has no internet connection`() = runBlocking {
        coEvery { fakeRepository.getMovieTrailer(1) } returns flow {
            emit(Resource.Error("No Internet Connection"))
        }
        getMovieTrailer(1).onEach { result ->
            assert(result is Resource.Error)
        }.launchIn(this)
        coVerify { fakeRepository.getMovieTrailer(1) }
    }

    @Test
    fun `emits ResourceSuccess when retrofit request is successful`() = runBlocking {
        val dummyData = MovieTrailer(
            id = 1,
            results = emptyList()
        )
        coEvery { fakeRepository.getMovieTrailer(1) } returns flow {
            emit(Resource.Success(dummyData))
        }
        getMovieTrailer(1).onEach { result ->
            assert(result is Resource.Success)
        }.launchIn(this)
        coVerify { fakeRepository.getMovieTrailer(1) }
    }
}