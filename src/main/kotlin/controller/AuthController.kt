package org.ebisur.controller

import org.ebisur.dto.LoginDto
import org.ebisur.dto.UserDto
import org.ebisur.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import jakarta.validation.Valid
import org.ebisur.dto.UserCreationDto

@RestController
@RequestMapping("/api/auth")
@Validated
class AuthController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody userDto: UserCreationDto): ResponseEntity<UserDto> {
        val registeredUser = userService.registerUser(userDto)
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser)
    }

    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody loginDto: LoginDto): ResponseEntity<Map<String, String>> {
        val token = userService.loginUser(loginDto)
        return ResponseEntity.ok(mapOf("token" to token))
    }
}