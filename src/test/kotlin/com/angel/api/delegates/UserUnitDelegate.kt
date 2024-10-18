package com.angel.api.delegates

import com.angel.api.models.dto.UserDTO
import com.angel.api.models.enums.Role
import io.github.serpro69.kfaker.Faker

class UserUnitDelegate {

    val faker = Faker()
    fun getInstance(): UserDTO {
        return UserDTO(
            name = faker.name.neutralFirstName(),
            surnames = faker.name.lastName(),
            role = Role.CUSTOMER,
            email = "${faker.company.name()}@gmail.com"
        )
    }
    fun areEquals(user1: UserDTO, user2: UserDTO): Boolean {
        return user1.name == user2.name &&
                user1.surnames == user2.surnames &&
                user1.email == user2.email &&
                user1.role == user2.role
    }
}