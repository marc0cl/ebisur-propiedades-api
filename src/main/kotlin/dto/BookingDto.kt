package org.ebisur.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class BookingDto(
    val id: UUID,
    val propertyId: UUID,
    val userId: UUID,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val totalPrice: BigDecimal,
    val status: String
)