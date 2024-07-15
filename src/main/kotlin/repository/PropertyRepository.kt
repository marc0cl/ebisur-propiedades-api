package org.ebisur.repository

import org.ebisur.model.Property
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Repository
interface PropertyRepository : JpaRepository<Property, UUID> {

    @Query("""
        SELECT DISTINCT p FROM Property p
        LEFT JOIN p.bookings b
        WHERE (:checkInDate IS NULL OR :checkOutDate IS NULL OR
               (b IS NULL OR b.checkOutDate < :checkInDate OR b.checkInDate > :checkOutDate))
        AND (:city IS NULL OR p.city = :city)
        AND (:country IS NULL OR p.country = :country)
        AND (:maxPrice IS NULL OR p.pricePerNight <= :maxPrice)
        AND (:minBedrooms IS NULL OR p.bedrooms >= :minBedrooms)
        AND (:minBathrooms IS NULL OR p.bathrooms >= :minBathrooms)
        AND (:minGuests IS NULL OR p.maxGuests >= :minGuests)
        AND p.isActive = true
    """)
    fun findAvailableProperties(
        @Param("checkInDate") checkInDate: LocalDate?,
        @Param("checkOutDate") checkOutDate: LocalDate?,
        @Param("city") city: String?,
        @Param("country") country: String?,
        @Param("maxPrice") maxPrice: BigDecimal?,
        @Param("minBedrooms") minBedrooms: Int?,
        @Param("minBathrooms") minBathrooms: Int?,
        @Param("minGuests") minGuests: Int?
    ): List<Property>

    @Query("""
        SELECT p FROM Property p
        WHERE p.isActive = true
        ORDER BY p.name
    """)
    fun findAllActiveProperties(): List<Property>

    fun findByIdAndIsActiveTrue(id: UUID): Property?
}