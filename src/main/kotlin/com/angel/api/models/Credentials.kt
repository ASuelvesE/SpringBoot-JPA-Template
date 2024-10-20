package com.angel.api.models

import com.angel.api.infrastructure.models.BasicEntity
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity
@Table(
    name = "credentials",
    indexes = [
        Index(name = "idx_user_id", columnList = "user_id"),
        Index(name = "idx_username", columnList = "username")
    ],
    uniqueConstraints = [
        UniqueConstraint(name = "uk_identifier_user_id", columnNames = ["identifier", "user_id"])
    ]
)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class Credentials (

    @Column(name = "identifier", nullable = false, unique = true)
    val identifier: String = "",

    @Column(name = "password", nullable = false)
    val pass: String = "",

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null
): BasicEntity(), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return pass
    }

    override fun getUsername(): String {
        return identifier
    }

}