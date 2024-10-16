package com.angel.api.models

import com.angel.api.models.enums.Role
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


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

        @Column(name = "password", nullable = false)
        val pass: String = "",

        @Enumerated(EnumType.STRING)
        var role: Role = Role.CUSTOMER
): BasicEntity(), UserDetails {

        override fun getAuthorities(): Collection<GrantedAuthority> {
                return listOf(SimpleGrantedAuthority(role.name))
        }

        override fun getPassword(): String {
                return pass
        }

        override fun getUsername(): String {
                return email
        }

        override fun isAccountNonExpired(): Boolean {
                return true
        }

        override fun isAccountNonLocked(): Boolean {
                return true
        }

        override fun isCredentialsNonExpired(): Boolean {
                return true
        }

        override fun isEnabled(): Boolean {
                return true
        }
}