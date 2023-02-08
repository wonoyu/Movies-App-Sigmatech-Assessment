package com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.use_case

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.model.MoviesByGenre
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.repository.MoviesByGenreRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class GetMoviesByGenreTest {
    private lateinit var getMoviesByGenre: GetMoviesByGenre

    @MockK
    private lateinit var fakeRepository: MoviesByGenreRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getMoviesByGenre = GetMoviesByGenre(fakeRepository)
    }

    @Test
    fun `emits ResourceError when failed to call http request`() = runBlocking {
        coEvery { fakeRepository.getMoviesByGenre("12", "1") } returns flow {
            emit(Resource.Error(""))
        }
        getMoviesByGenre("12", "1").onEach { result ->
            assert(result is Resource.Error)
        }.launchIn(this)
        coVerify { fakeRepository.getMoviesByGenre("12", "1") }
    }

    @Test
    fun `emits ResourceError when device has no internet connection`() = runBlocking {
        coEvery { fakeRepository.getMoviesByGenre("12", "1") } returns flow {
            emit(Resource.Error(""))
        }
        getMoviesByGenre("12", "1").onEach { result ->
            assert(result is Resource.Error)
        }.launchIn(this)
        coVerify { fakeRepository.getMoviesByGenre("12", "1") }
    }

    @Test
    fun `emits ResourceSuccess when retrofit request is successful`() = runBlocking {
        val dummyData = MoviesByGenre(page = 1, results = emptyList(), total_pages = 1, total_results = 1)
        coEvery { fakeRepository.getMoviesByGenre("12", "1") } returns flow {
            emit(Resource.Success(dummyData))
        }
        getMoviesByGenre("12", "1").onEach { result ->
            assert(result is Resource.Success)
        }.launchIn(this)
        coVerify { fakeRepository.getMoviesByGenre("12", "1") }
    }
}