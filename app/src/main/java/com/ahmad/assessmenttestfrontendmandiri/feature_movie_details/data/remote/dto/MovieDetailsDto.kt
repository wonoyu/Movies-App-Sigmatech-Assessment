package com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.data.remote.dto

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.model.MovieDetails
import com.ahmad.assessmenttestfrontendmandiri.feature_movie_genres.data.remote.dto.GenreDto

data class MovieDetailsDto(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: Any?,
    val budget: Int,
    val genres: List<GenreDto>,
    val homepage: String,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<ProductionCompanyDto>,
    val production_countries: List<ProductionCountryDto>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguageDto>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    fun toMoviesDetail(): MovieDetails {
        return MovieDetails(
            adult = adult,
            backdrop_path = backdrop_path,
            belongs_to_collection = belongs_to_collection,
            budget = budget,
            genres = genres.map { it.toGenre() },
            homepage = homepage,
            id = id,
            imdb_id = imdb_id,
            original_language = original_language,
            original_title = original_title,
            overview = overview,
            popularity = popularity,
            poster_path = poster_path,
            production_companies = production_companies.map { it.toProductionCompany() },
            production_countries = production_countries.map { it.toProductionCountry() },
            release_date = release_date,
            revenue = revenue,
            runtime = runtime,
            spoken_languages = spoken_languages.map { it.toSpokenLanguage() },
            status = status,
            tagline = tagline,
            title = title,
            video = video,
            vote_average = vote_average,
            vote_count = vote_count,
        )
    }
}