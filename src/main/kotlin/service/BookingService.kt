package org.ebisur.service

import org.ebisur.dto.BookingDto
import org.ebisur.dto.BookingSearchRequestDto
import org.ebisur.model.Booking
import org.ebisur.model.BookingStatus
import org.ebisur.repository.BookingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
class BookingService(private val bookingRepository: BookingRepository) {

    @Transactional(readOnly = true)
    fun getBookingsInDateRange(searchRequest: BookingSearchRequestDto): List<BookingDto> {
        return bookingRepository.findBookingsInDateRange(
            searchRequest.startDate,
            searchRequest.endDate
        ).map { booking ->
            BookingDto(
                id = booking.id,
                propertyId = booking.property.id,
                userId = booking.user.id,
                checkInDate = booking.checkInDate,
                checkOutDate = booking.checkOutDate,
                totalPrice = booking.totalPrice,
                status = booking.status.name
            )
        }
    }

    @Transactional
    fun cancelBooking(bookingId: UUID) {
        val booking = bookingRepository.findById(bookingId)
            .orElseThrow { IllegalArgumentException("Booking not found") }

        // Verificar si la reserva puede ser cancelada (por ejemplo, si está dentro del período de cancelación)
        if (!canCancelBooking(booking)) {
            throw IllegalStateException("Booking cannot be cancelled")
        }

        booking.status = BookingStatus.CANCELLED
        bookingRepository.save(booking)
    }

    private fun canCancelBooking(booking: Booking): Boolean {
        // Implementar lógica para verificar si la reserva puede ser cancelada
        // Por ejemplo, verificar si la fecha de check-in es al menos 24 horas en el futuro
        return booking.checkInDate.isAfter(LocalDate.now().plusDays(1))
    }
}