package com.angel.api


import com.angel.api.containers.MySqlContainerConfiguration
import com.angel.api.containers.PostgresSqlContainerConfiguration
import com.angel.api.models.dto.request.LoginRequest
import com.angel.api.models.enums.Role
import com.angel.api.services.AuthService
import com.angel.api.services.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import


//@Import(PostgresSqlContainerConfiguration::class)
@Import(MySqlContainerConfiguration::class)
@SpringBootTest
class UserUnitTestLauncher {

	@Autowired
	lateinit var userService: UserService

	@Autowired
	lateinit var authService: AuthService

	@Test
	fun `register and login`() {
		val user = authService.createUser("name1","surnames1","a@gmail.com","12345",Role.CUSTOMER)
		assert(user.email == "a@gmail.com")

		val loginResponse = authService.login(LoginRequest(user.email,"12345"))
		Assertions.assertNotNull(loginResponse.token)
	}
	@Test
	fun `find by email`() {
		val user = authService.createUser("name2","surnames2","a2@gmail.com","12345",Role.CUSTOMER)
		assert(user.email == "a2@gmail.com")

		val found = userService.getByEmail(user.email)
		assert(user.id == found.id)
	}

}
