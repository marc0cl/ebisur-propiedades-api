package org.ebisur.service

import org.ebisur.dto.PropertyAvailabilityDto
import org.ebisur.dto.PropertySearchRequestDto
import org.ebisur.model.Property
import org.ebisur.repository.PropertyRepository
import org.ebisur.repository.BookingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
class PropertyService(
    private val propertyRepository: PropertyRepository,
    private val bookingRepository: BookingRepository
) {

    @Transactional(readOnly = true)
    fun getAvailableProperties(searchRequest: PropertySearchRequestDto): List<PropertyAvailabilityDto> {
        val properties = propertyRepository.findAvailableProperties(
            searchRequest.checkInDate,
            searchRequest.checkOutDate,
            searchRequest.city,
            searchRequest.country,
            searchRequest.maxPrice,
            searchRequest.minBedrooms,
            searchRequest.minBathrooms,
            searchRequest.minGuests
        )

        return properties.map { property: Property ->
            val isAvailable = !bookingRepository.existsOverlappingBooking(
                property.id,
                searchRequest.checkInDate,
                searchRequest.checkOutDate
            )
            PropertyAvailabilityDto(
                id = property.id.toString(),
                name = property.name,
                description = property.description,
                address = property.address,
                city = property.city,
                country = property.country,
                pricePerNight = property.pricePerNight,
                bedrooms = property.bedrooms,
                bathrooms = property.bathrooms,
                maxGuests = property.maxGuests,
                isAvailable = isAvailable
            )
        }
    }

    @Transactional(readOnly = true)
    fun getAllPropertiesWithAvailability(date: LocalDate): List<PropertyAvailabilityDto> {
        val properties = propertyRepository.findAllActiveProperties()
        return properties.map { property: Property ->
            val isAvailable = !bookingRepository.existsActiveBookingForPropertyOnDate(
                property.id,
                date
            )
            PropertyAvailabilityDto(
                id = property.id.toString(),
                name = property.name,
                description = property.description,
                address = property.address,
                city = property.city,
                country = property.country,
                pricePerNight = property.pricePerNight,
                bedrooms = property.bedrooms,
                bathrooms = property.bathrooms,
                maxGuests = property.maxGuests,
                isAvailable = isAvailable
            )
        }
    }
}