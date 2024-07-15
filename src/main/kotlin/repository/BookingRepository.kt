package org.ebisur.repository

import org.ebisur.model.Booking
import org.ebisur.model.BookingStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.UUID

@Repository
interface BookingRepository : JpaRepository<Booking, UUID> {

    @Query("""
        SELECT b FROM Booking b
        WHERE b.checkInDate <= :endDate AND b.checkOutDate >= :startDate
        AND b.status NOT IN ('CANCELLED', 'COMPLETED')
        ORDER BY b.checkInDate
    """)
    fun findBookingsInDateRange(
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<Booking>

    @Query("""
        SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b
        WHERE b.property.id = :propertyId
        AND b.status NOT IN ('CANCELLED', 'COMPLETED')
        AND ((b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate)
             OR (b.checkInDate >= :checkInDate AND b.checkInDate < :checkOutDate))
    """)
    fun existsOverlappingBooking(
        @Param("propertyId") propertyId: UUID,
        @Param("checkInDate") checkInDate: LocalDate,
        @Param("checkOutDate") checkOutDate: LocalDate
    ): Boolean

    @Query("""
        SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b
        WHERE b.property.id = :propertyId
        AND b.status NOT IN ('CANCELLED', 'COMPLETED')
        AND :date BETWEEN b.checkInDate AND b.checkOutDate
    """)
    fun existsActiveBookingForPropertyOnDate(
        @Param("propertyId") propertyId: UUID,
        @Param("date") date: LocalDate
    ): Boolean

    fun findByIdAndStatus(id: UUID, status: BookingStatus): Booking?

    fun findByUserIdAndStatusNotIn(userId: UUID, statuses: List<BookingStatus>): List<Booking>
}