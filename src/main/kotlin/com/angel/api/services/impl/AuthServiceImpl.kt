package com.angel.api.impl.services

import com.angel.api.models.enums.Role
import com.angel.api.models.User
import com.angel.api.infrastructure.exceptions.ApiException
import com.angel.api.models.Credentials
import com.angel.api.models.dto.UserDTO
import com.angel.api.models.dto.request.LoginRequest
import com.angel.api.models.dto.response.LoginResponse
import com.angel.api.repositories.CredentialsRepository
import com.angel.api.repositories.UserRepository
import com.angel.api.services.AuthService
import com.angel.api.utils.Utils
import com.angel.api.utils.converters.UserDataConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.io.println


@Service
class AuthServiceImpl : AuthService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var credentialsRepository: CredentialsRepository


    override fun createUser(name: String, surnames: String, email: String, rawPassword: String, role: Role): UserDTO {
        try {
            val hashedPassword = Utils.encodePassword(rawPassword)

            val user = userRepository.save(User(name, surnames, email, role, null))
            credentialsRepository.save(Credentials(UUID.randomUUID().toString(),hashedPassword, user = user))
            return UserDataConverter.toDTO(user)
        }catch (e: Exception){
            throw ApiException(HttpStatus.BAD_REQUEST,"80/400","Mail already exists")
        }
    }

    override fun login(loginRequest: LoginRequest): LoginResponse {
        try {
            val user = userRepository.findByEmail(loginRequest.email)
                ?: throw ApiException(HttpStatus.FOUND,"80/404",loginRequest.email)

            if (Utils.matches(loginRequest.password, user.credentials?.password!!))
                return LoginResponse(loginRequest.email,loginRequest.password,Utils.generateToken(user))
            throw ApiException(HttpStatus.UNAUTHORIZED,"80/401","Incorrect password")
        }catch (e: Exception){
            throw e
        }
    }
}
