package org.ebisur.controller

import org.ebisur.dto.BookingDto
import org.ebisur.dto.BookingSearchRequestDto
import org.ebisur.service.BookingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/bookings")
class BookingController(private val bookingService: BookingService) {

    @GetMapping
    fun getBookingsInDateRange(searchRequest: BookingSearchRequestDto): ResponseEntity<List<BookingDto>> {
        val bookings = bookingService.getBookingsInDateRange(searchRequest)
        return ResponseEntity.ok(bookings)
    }

    @DeleteMapping("/{id}")
    fun cancelBooking(@PathVariable id: UUID): ResponseEntity<Unit> {
        bookingService.cancelBooking(id)
        return ResponseEntity.noContent().build()
    }
}