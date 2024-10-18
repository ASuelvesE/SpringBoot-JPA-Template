package com.angel.api.repositories

import com.angel.api.models.Credentials
import com.angel.api.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CredentialsRepository : JpaRepository<Credentials, UUID> {
    fun findByIdentifier(identifier: String): Credentials?
}
