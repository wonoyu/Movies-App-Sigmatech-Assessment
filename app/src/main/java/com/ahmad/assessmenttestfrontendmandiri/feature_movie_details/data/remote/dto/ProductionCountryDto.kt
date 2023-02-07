package com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.data.remote.dto

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.model.ProductionCountry

data class ProductionCountryDto(
    val iso_3166_1: String,
    val name: String
) {
    fun toProductionCountry(): ProductionCountry {
        return ProductionCountry(
            iso_3166_1 = iso_3166_1,
            name = name,
        )
    }
}