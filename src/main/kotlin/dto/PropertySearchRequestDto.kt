package org.ebisur.dto

import java.math.BigDecimal
import java.time.LocalDate

data class PropertySearchRequestDto(
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val city: String? = null,
    val country: String? = null,
    val maxPrice: BigDecimal? = null,
    val minBedrooms: Int? = null,
    val minBathrooms: Int? = null,
    val minGuests: Int? = null
)