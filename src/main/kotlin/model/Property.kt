package org.ebisur.model

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "properties")
data class Property(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    val host: User,

    @Column(nullable = false)
    val name: String,

    @Column(columnDefinition = "TEXT")
    val description: String,

    @Column(nullable = false)
    val address: String,

    @Column(nullable = false)
    val city: String,

    @Column(nullable = false)
    val country: String,

    @Column(name = "price_per_night", nullable = false)
    val pricePerNight: BigDecimal,

    @Column(nullable = false)
    val bedrooms: Int,

    @Column(nullable = false)
    val bathrooms: Int,

    @Column(name = "max_guests", nullable = false)
    val maxGuests: Int,

    @Column(precision = 10, scale = 8)
    val latitude: BigDecimal?,

    @Column(precision = 11, scale = 8)
    val longitude: BigDecimal?,

    @Column(name = "is_active")
    val isActive: Boolean = true,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "property", cascade = [CascadeType.ALL], orphanRemoval = true)
    val bookings: MutableList<Booking> = mutableListOf(),

    @ElementCollection
    @CollectionTable(name = "property_amenities", joinColumns = [JoinColumn(name = "property_id")])
    @Column(name = "amenity_name")
    val amenities: MutableSet<String> = mutableSetOf()
)