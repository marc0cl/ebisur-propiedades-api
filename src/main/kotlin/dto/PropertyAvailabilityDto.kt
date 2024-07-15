package org.ebisur.dto

import java.math.BigDecimal

data class PropertyAvailabilityDto(
    val id: String,
    val name: String,
    val description: String,
    val address: String,
    val city: String,
    val country: String,
    val pricePerNight: BigDecimal,
    val bedrooms: Int,
    val bathrooms: Int,
    val maxGuests: Int,
    val isAvailable: Boolean
)