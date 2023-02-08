package com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.use_case

import com.ahmad.assessmenttestfrontendmandiri.core.util.Resource
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.model.MoviesByGenre
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.repository.MoviesByGenreRepository
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_by_genres.domain.use_case.GetMoviesByGenre
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.model.MovieDetails
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.repository.MovieDetailsRepository
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

class GetMovieDetailsTest {
    private lateinit var getMovieDetails: GetMovieDetails

    @MockK
    private lateinit var fakeRepository: MovieDetailsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getMovieDetails = GetMovieDetails(fakeRepository)
    }

    @Test
    fun `emits ResourceError when failed to call http request`() = runBlocking {
        coEvery { fakeRepository.getMovieDetails(1) } returns flow {
            emit(Resource.Error("Http Error"))
        }
        getMovieDetails(1).onEach { result ->
            assert(result is Resource.Error)
        }.launchIn(this)
        coVerify { fakeRepository.getMovieDetails(1) }
    }

    @Test
    fun `emits ResourceError when device has no internet connection`() = runBlocking {
        coEvery { fakeRepository.getMovieDetails(1) } returns flow {
            emit(Resource.Error("No Internet Connection"))
        }
        getMovieDetails(1).onEach { result ->
            assert(result is Resource.Error)
        }.launchIn(this)
        coVerify { fakeRepository.getMovieDetails(1) }
    }

    @Test
    fun `emits ResourceSuccess when retrofit request is successful`() = runBlocking {
        val dummyData = MovieDetails(
            adult = false,
            backdrop_path = "",
            belongs_to_collection = null,
            budget = 100,
            genres = emptyList(),
            homepage = "",
            id = 1,
            imdb_id = "",
            original_language = "",
            original_title = "",
            overview = "",
            popularity = 1.0,
            poster_path = "",
            production_companies = emptyList(),
            production_countries = emptyList(),
            release_date = "",
            revenue = 100,
            runtime = 100,
            spoken_languages = emptyList(),
            status = "",
            tagline = "",
            title = "",
            video = false,
            vote_average = 1.0,
            vote_count = 1,
        )
        coEvery { fakeRepository.getMovieDetails(1) } returns flow {
            emit(Resource.Success(dummyData))
        }
        getMovieDetails(1).onEach { result ->
            assert(result is Resource.Success)
        }.launchIn(this)
        coVerify { fakeRepository.getMovieDetails(1) }
    }
}