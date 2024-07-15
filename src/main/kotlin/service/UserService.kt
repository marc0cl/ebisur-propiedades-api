package org.ebisur.service

import org.ebisur.dto.LoginDto
import org.ebisur.dto.UserCreationDto
import org.ebisur.dto.UserDto
import org.ebisur.dto.UserUpdateDto
import org.ebisur.model.User
import org.ebisur.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    @Transactional
    fun registerUser(userCreationDto: UserCreationDto): UserDto {
        val user = User(
            name = userCreationDto.name,
            email = userCreationDto.email,
            password = passwordEncoder.encode(userCreationDto.password),
            dateOfBirth = userCreationDto.dateOfBirth
        )
        val savedUser = userRepository.save(user)
        return UserDto(savedUser.name, savedUser.username, savedUser.dateOfBirth, savedUser.role)
    }

    fun loginUser(loginDto: LoginDto): String {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
        )
        val user = userRepository.findByEmail(loginDto.email)
            ?: throw UsernameNotFoundException("User not found")
        return jwtService.generateToken(user)
    }

    fun getUser(id: UUID): UserDto {
        val user = userRepository.findById(id).orElseThrow { UsernameNotFoundException("User not found") }
        return UserDto(user.name, user.username, user.dateOfBirth, user.role)
    }

    @Transactional
    fun updateUser(id: UUID, userUpdateDto: UserUpdateDto): UserDto {
        val user = userRepository.findById(id).orElseThrow { UsernameNotFoundException("User not found") }

        // Verificar si el usuario actual tiene permiso para actualizar este usuario
        val currentUser = SecurityContextHolder.getContext().authentication.principal as User
        if (currentUser.id != user.id && !currentUser.authorities.any { it.authority == "ROLE_ADMIN" }) {
            throw IllegalAccessException("You don't have permission to update this user")
        }

        // Verificar la contraseña actual
        if (!passwordEncoder.matches(userUpdateDto.currentPassword, user.password)) {
            throw IllegalArgumentException("Current password is incorrect")
        }

        val updatedUser = user.copy(
            name = userUpdateDto.name,
            email = userUpdateDto.email,
            password = if (userUpdateDto.newPassword.isNotBlank()) passwordEncoder.encode(userUpdateDto.newPassword) else user.password,
            dateOfBirth = userUpdateDto.dateOfBirth
        )
        val savedUser = userRepository.save(updatedUser)
        return UserDto(savedUser.name, savedUser.username, savedUser.dateOfBirth, savedUser.role)
    }

    @Transactional
    fun updateUserPasswordByAdmin(id: UUID, newPassword: String) {
        val currentUser = SecurityContextHolder.getContext().authentication.principal as User
        if (!currentUser.authorities.any { it.authority == "ROLE_ADMIN" }) {
            throw IllegalAccessException("Only admins can update other users' passwords")
        }

        val user = userRepository.findById(id).orElseThrow { UsernameNotFoundException("User not found") }
        val updatedUser = user.copy(
            password = passwordEncoder.encode(newPassword)
        )
        userRepository.save(updatedUser)
    }

    @Transactional
    fun deleteUser(id: UUID) {
        if (!userRepository.existsById(id)) {
            throw UsernameNotFoundException("User not found")
        }
        userRepository.deleteById(id)
    }
}