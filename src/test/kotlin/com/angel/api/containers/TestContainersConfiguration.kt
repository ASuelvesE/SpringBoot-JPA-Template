package com.angel.api.containers

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestContainersConfiguration {

	private val databaseType: DatabaseType = DatabaseType.POSTGRESQL

	@Bean
	@ServiceConnection
	fun databaseContainer(): Any {
		return when (databaseType) {
			DatabaseType.MYSQL -> {
				MySQLContainer(DockerImageName.parse("mysql:latest")).apply {
					withDatabaseName("test")
					withUsername("test")
					withPassword("test")
					start()
				}
			}
			DatabaseType.POSTGRESQL -> {
				PostgreSQLContainer(DockerImageName.parse("postgres:latest")).apply {
					withDatabaseName("test")
					withUsername("test")
					withPassword("test")
					start()
				}
			}
		}
	}
}
enum class DatabaseType {
	MYSQL,
	POSTGRESQL
}