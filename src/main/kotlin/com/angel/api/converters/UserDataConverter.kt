package com.angel.api.converters


import com.angel.api.models.User
import com.angel.api.models.dto.UserDTO

object UserDataConverter {
    fun fromDTO(userDTO: UserDTO): User {
        val user = User().apply {
            this.id = userDTO.id
            this.name = userDTO.name
            this.surnames = userDTO.surnames
            this.email = userDTO.email
            this.role = userDTO.role
        }
        return user
    }

    fun toDTO(user: User): UserDTO {
        return UserDTO(
            id = user.id,
            name = user.name,
            surnames = user.surnames,
            email = user.email,
            role = user.role
        )
    }
}
