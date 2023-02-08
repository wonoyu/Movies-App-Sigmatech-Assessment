package com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.use_case

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.model.Genre
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.domain.repository.MovieGenresRepository
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

class GetMovieGenresTest {
    private lateinit var getMovieGenres: GetMovieGenres

    @MockK
    private lateinit var fakeRepository: MovieGenresRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getMovieGenres = GetMovieGenres(fakeRepository)
    }

    @Test
    fun `emits ResourceError when failed to call http request`() = runBlocking {
        coEvery { fakeRepository.getMovieGenres() } returns flow {
            emit(Resource.Error("Http Error"))
        }
        getMovieGenres().onEach { result ->
            assert(result is Resource.Error)
        }.launchIn(this)
        coVerify { fakeRepository.getMovieGenres() }
    }

    @Test
    fun `emits ResourceError when device has no internet connection`() = runBlocking {
        coEvery { fakeRepository.getMovieGenres() } returns flow {
            emit(Resource.Error("No Internet Connection"))
        }
        getMovieGenres().onEach { result ->
            assert(result is Resource.Error)
        }.launchIn(this)
        coVerify { fakeRepository.getMovieGenres() }
    }

    @Test
    fun `emits ResourceSuccess when retrofit request is successful`() = runBlocking {
        val dummyData = listOf<Genre>()
        coEvery { fakeRepository.getMovieGenres() } returns flow {
            emit(Resource.Success(dummyData))
        }
        getMovieGenres().onEach { result ->
            assert(result is Resource.Success)
        }.launchIn(this)
        coVerify { fakeRepository.getMovieGenres() }
    }
}