package com.angel.api.models

import com.angel.api.models.enums.Role
import jakarta.persistence.*


@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class User(
        @Column(name = "name", nullable = false)
        val name: String = "",

        @Column(name = "surnames", nullable = false)
        val surnames: String = "",

        @Column(name = "email", nullable = false, unique = true)
        val email: String = "",

        @Column(name = "password", nullable = false)
        val password: String = "",

        @Enumerated(EnumType.STRING)
        val role: Role = Role.CUSTOMER
): BasicEntity()