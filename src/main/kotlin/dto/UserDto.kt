package org.ebisur.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import org.ebisur.model.Role
import java.time.LocalDate

data class UserDto(
    val name: String,
    val email: String,
    val dateOfBirth: LocalDate,
    val role: Role
)

data class UserCreationDto(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password is required")
    val password: String,

    @field:Past(message = "Date of birth must be in the past")
    val dateOfBirth: LocalDate,

    val role: Role = Role.USER
)

data class LoginDto(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password is required")
    val password: String
)