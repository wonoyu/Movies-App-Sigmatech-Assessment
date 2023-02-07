package com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.data.remote.dto

import com.ahmad.assessmenttestfrontendmandiri.feature_movie_details.domain.model.ProductionCompany

data class ProductionCompanyDto(
    val id: Int,
    val logo_path: String?,
    val name: String,
    val origin_country: String
) {
    fun toProductionCompany(): ProductionCompany {
        return ProductionCompany(
            id = id,
            logo_path = logo_path,
            name = name,
            origin_country = origin_country,
        )
    }
}