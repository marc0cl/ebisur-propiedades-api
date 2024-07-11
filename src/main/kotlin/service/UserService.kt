package org.ebisur.service

import org.ebisur.dto.LoginDto
import org.ebisur.dto.UserDto
import org.ebisur.model.User
import org.ebisur.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    @Transactional
    fun registerUser(userDto: UserDto): UserDto {
        val user = User(
            name = userDto.name,
            email = userDto.email,
            password = passwordEncoder.encode(userDto.password),
            dateOfBirth = userDto.dateOfBirth,
            insertionDate = LocalDateTime.now()
        )
        val savedUser = userRepository.save(user)
        return UserDto(savedUser.id, savedUser.name, savedUser.username, "", savedUser.dateOfBirth)
    }

    fun loginUser(loginDto: LoginDto): String {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password)
        )
        val user = userRepository.findByEmail(loginDto.email)
            ?: throw UsernameNotFoundException("User not found")
        return jwtService.generateToken(user)
    }

    fun getUser(id: Long): UserDto {
        val user = userRepository.findById(id).orElseThrow { UsernameNotFoundException("User not found") }
        return UserDto(user.id, user.name, user.username, "", user.dateOfBirth)
    }

    @Transactional
    fun updateUser(id: Long, userDto: UserDto): UserDto {
        val user = userRepository.findById(id).orElseThrow { UsernameNotFoundException("User not found") }
        val updatedUser = user.copy(
            name = userDto.name,
            email = userDto.email,
            password = if (userDto.password.isNotBlank()) passwordEncoder.encode(userDto.password) else user.password,
            dateOfBirth = userDto.dateOfBirth
        )
        val savedUser = userRepository.save(updatedUser)
        return UserDto(savedUser.id, savedUser.name, savedUser.username, "", savedUser.dateOfBirth)
    }

    @Transactional
    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw UsernameNotFoundException("User not found")
        }
        userRepository.deleteById(id)
    }
}
