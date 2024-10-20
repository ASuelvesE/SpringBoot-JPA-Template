package com.angel.api.models

import com.angel.api.infrastructure.models.BasicEntity
import com.angel.api.models.enums.Role
import jakarta.persistence.*


@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class User(
        @Column(name = "name", nullable = false)
        var name: String = "",

        @Column(name = "surnames", nullable = false)
        var surnames: String = "",

        @Column(name = "email", nullable = false, unique = true)
        var email: String = "",

        @Enumerated(EnumType.STRING)
        var role: Role = Role.CUSTOMER,

        @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        var credentials: Credentials? = null
): BasicEntity()