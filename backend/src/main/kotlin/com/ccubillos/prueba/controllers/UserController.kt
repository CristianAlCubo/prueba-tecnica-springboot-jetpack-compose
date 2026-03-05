package com.ccubillos.prueba.controllers

import com.ccubillos.prueba.dto.ApiResponseDTO
import com.ccubillos.prueba.dto.user.CreateUserDTO
import com.ccubillos.prueba.dto.user.UserDTO
import com.ccubillos.prueba.models.User
import com.ccubillos.prueba.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    var userService: UserService
) {

    @GetMapping()
    fun getAll() = ResponseEntity.ok(ApiResponseDTO<List<UserDTO>>(HttpStatus.OK, data = userService.findAll().map { UserDTO(it) }))

    @GetMapping("/{username}")
    fun getByUsername(@PathVariable username: String): ResponseEntity<ApiResponseDTO<UserDTO>> {

        val user = userService.findByUsername(username)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponseDTO(
                    status = HttpStatus.NOT_FOUND,
                    data = null
                )
            )

        return ResponseEntity.ok(
            ApiResponseDTO(
                status = HttpStatus.OK,
                data = UserDTO(user)
            )
        )
    }

    @PostMapping()
    fun saveUser(@RequestBody user : CreateUserDTO): ResponseEntity<ApiResponseDTO<UserDTO>> {
        if(userService.existsByUsername(user.username)){
            return ResponseEntity.badRequest().body(ApiResponseDTO<UserDTO>(
                status = HttpStatus.BAD_REQUEST,
                error = "Username already exists",
            ))
        }

        return ResponseEntity.ok(ApiResponseDTO(HttpStatus.OK, data = UserDTO(userService.createUser(user.username))));
    }
}