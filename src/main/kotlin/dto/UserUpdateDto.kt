package org.ebisur.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import java.time.LocalDate

data class UserUpdateDto(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:Past(message = "Date of birth must be in the past")
    val dateOfBirth: LocalDate,

    @field:NotBlank(message = "Current password is required")
    val currentPassword: String,

    val newPassword: String = ""
)