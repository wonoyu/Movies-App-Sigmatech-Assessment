package com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.data.remote.dto

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.model.SpokenLanguage

data class SpokenLanguageDto(
    val english_name: String,
    val iso_639_1: String,
    val name: String
) {
    fun toSpokenLanguage(): SpokenLanguage {
        return SpokenLanguage(
            english_name = english_name,
            iso_639_1 = iso_639_1,
            name = name,
        )
    }
}