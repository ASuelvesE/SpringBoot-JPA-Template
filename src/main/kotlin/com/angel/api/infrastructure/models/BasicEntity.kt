package com.angel.api.infrastructure.models

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@MappedSuperclass
class BasicEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID = UUID.randomUUID(),

    @Column(name = "created", nullable = false)
    val created: LocalDateTime = LocalDateTime.now(),

    @Column(name = "modified", nullable = false)
    val modified: LocalDateTime = LocalDateTime.now(),

    @Column(name = "tenant", nullable = true)
    val tenant: UUID? = null
) {

}