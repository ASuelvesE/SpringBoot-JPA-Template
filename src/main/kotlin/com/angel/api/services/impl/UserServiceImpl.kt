package com.angel.api.services.impl

import com.angel.api.configs.SessionManager
import com.angel.api.utils.converters.UserDataConverter
import com.angel.api.infrastructure.exceptions.ApiException
import com.angel.api.models.User
import com.angel.api.models.dto.UserDTO
import com.angel.api.repositories.CredentialsRepository
import com.angel.api.repositories.UserRepository
import com.angel.api.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl : UserService, UserDetailsService {

    @Autowired
    lateinit var sessionManager: SessionManager

    @Autowired
    lateinit var userRepository: UserRepository
    override fun update(userDTO: UserDTO): UserDTO {
        try {
            val user = userRepository.findById(userDTO.id).orElseThrow {
                ApiException(HttpStatus.NOT_FOUND, "80/404", "User not found with ID: ${userDTO.id}")
            }
            this.merge(user,UserDataConverter.fromDTO(userDTO))
            userRepository.save(user)
            return UserDataConverter.toDTO(user)
        } catch (e: Exception) {
            throw e
        }
    }
    private fun merge(source: User, destination: User){
        source.email = destination.email
        source.name = destination.name
        source.role = destination.role
        source.surnames = destination.surnames
    }


    override fun getById(id: UUID): UserDTO {
        try {
            val user = userRepository.findById(id).orElseThrow {
                ApiException(HttpStatus.NOT_FOUND, "80/404", "User not found with ID: $id")
            }
            return UserDataConverter.toDTO(user)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getByEmail(email: String): UserDTO {
        try {
            println("Token: ${sessionManager.getToken()}")
            val user = userRepository.findByEmail(email) ?: throw ApiException(HttpStatus.NOT_FOUND, "80/404", "User not found with email: $email")
            return UserDataConverter.toDTO(user)
        } catch (e: Exception) {
            throw e
        }
    }

    override fun findAllPaginated(offset: Int, limit: Int): Page<UserDTO> {
        try {
            val pageable: Pageable = PageRequest.of((offset / limit), limit, Sort.by(Sort.Direction.DESC, "created"))
            val page = userRepository.findAll(pageable)
            return page.map { UserDataConverter.toDTO(it) }
        } catch (e: Exception) {
            throw e
        }
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw ApiException(HttpStatus.NOT_FOUND, "80/404", "User not found with email: $email")
        return org.springframework.security.core.userdetails.User(
            user.credentials?.username,
            "",
            mutableListOf()
        )
    }

}
