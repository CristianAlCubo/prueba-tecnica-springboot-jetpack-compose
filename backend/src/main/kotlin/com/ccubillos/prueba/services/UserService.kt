package com.ccubillos.prueba.services

import com.ccubillos.prueba.models.User
import com.ccubillos.prueba.repository.IUserRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(
    val userRepository : IUserRepository
) {

    fun findAll() = userRepository.findAll()
    fun findByUsername(username: String): User? = userRepository.findByUsername(username)
    fun existsByUsername(username: String) : Boolean = userRepository.existsByUsername(username)
    fun createUser(username : String): User = userRepository.save(User(username))
}