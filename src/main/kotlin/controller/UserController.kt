package org.ebisur.controller

import org.ebisur.dto.UserDto
import org.ebisur.dto.UserUpdateDto
import org.ebisur.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import java.util.*

@RestController
@RequestMapping("/api/users")
@Validated
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    fun getUser(@PathVariable id: UUID): ResponseEntity<UserDto> {
        val user = userService.getUser(id)
        return ResponseEntity.ok(user)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    fun updateUser(@PathVariable id: UUID, @Valid @RequestBody userUpdateDto: UserUpdateDto): ResponseEntity<UserDto> {
        val updatedUser = userService.updateUser(id, userUpdateDto)
        return ResponseEntity.ok(updatedUser)
    }

    @PutMapping("/{id}/reset-password")
    @PreAuthorize("hasRole('ADMIN')")
    fun resetUserPassword(@PathVariable id: UUID, @RequestParam newPassword: String): ResponseEntity<Unit> {
        userService.updateUserPasswordByAdmin(id, newPassword)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun deleteUser(@PathVariable id: UUID): ResponseEntity<Unit> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}