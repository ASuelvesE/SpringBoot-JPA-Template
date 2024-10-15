package com.angel.api.impl.services

import com.angel.api.models.enums.Role
import com.angel.api.models.User
import com.angel.api.exceptions.ApiException
import com.angel.api.models.dto.request.LoginRequest
import com.angel.api.models.dto.response.LoginResponse
import com.angel.api.repositories.UserRepository
import com.angel.api.services.AuthService
import com.angel.api.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import kotlin.io.println


@Service
class AuthServiceImpl : AuthService {

    @Autowired
    lateinit var userRepository: UserRepository


    override fun createUser(name: String, surnames: String, email: String, rawPassword: String, role: Role): User {
        try {
            val hashedPassword = Utils.encodePassword(rawPassword)
            val user = User(name, surnames, email, hashedPassword, role)
            return userRepository.save(user)
        }catch (e: Exception){
            throw ApiException(HttpStatus.BAD_REQUEST,"80/400",e.message)
        }
    }

    override fun findByEmail(email: String): User {
        try {
            return userRepository.findByEmail(email)
                ?: throw ApiException(HttpStatus.FOUND,"80/404",email)
        }catch (e: Exception){
            throw e
        }
    }

    override fun login(loginRequest: LoginRequest): LoginResponse {
        try {
            val user = userRepository.findByEmail(loginRequest.email)
                ?: throw ApiException(HttpStatus.FOUND,"80/404",loginRequest.email)

            if (Utils.matches(loginRequest.password, user.password))
                return LoginResponse(loginRequest.email,loginRequest.password,Utils.generateToken(user.email))
            throw ApiException(HttpStatus.UNAUTHORIZED,"80/401","Incorrect password")
        }catch (e: Exception){
            throw e
        }
    }
}
