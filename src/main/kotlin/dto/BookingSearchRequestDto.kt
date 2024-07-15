package org.ebisur.dto

import java.time.LocalDate

data class BookingSearchRequestDto(
    val startDate: LocalDate,
    val endDate: LocalDate
)