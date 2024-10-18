package com.angel.api


import com.angel.api.containers.MySqlContainerConfiguration
import com.angel.api.containers.PostgresSqlContainerConfiguration
import com.angel.api.delegates.UserUnitDelegate
import com.angel.api.exceptions.ApiException
import com.angel.api.models.dto.UserDTO
import com.angel.api.models.dto.request.LoginRequest
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
class UserUnitTestLauncher() {

	val delegate: UserUnitDelegate = UserUnitDelegate()

	@Autowired
	lateinit var userService: UserService

	@Autowired
	lateinit var authService: AuthService

	fun createUser(): UserDTO {
		val userDTO = delegate.getInstance()
		return authService.createUser(
			name = userDTO.name,
			surnames = userDTO.surnames,
			email = userDTO.email,
			rawPassword = "123456",
			role = userDTO.role
		)
	}

	@Test
	fun `register and login`() {
		val user = createUser()

		val loginResponse = authService.login(LoginRequest(user.email,"123456"))
		Assertions.assertNotNull(loginResponse.token)
	}
	@Test
	fun `find by email`() {
		val user = createUser()

		val found = userService.getByEmail(user.email)
		assert(delegate.areEquals(user,found))
	}
	@Test
	fun `update user`() {
		val user = createUser()
		user.name = "UpdatedName"
		val updatedUser = userService.update(user)

		assert(delegate.areEquals(user,updatedUser))
	}

	@Test
	fun `get user by ID`() {
		val user = createUser()
		val foundUser = userService.getById(user.id)

		assert(delegate.areEquals(user,foundUser))
	}

	@Test
	fun `find all users paginated`() {
		createUser()
		createUser()

		val page = userService.findAllPaginated(0, 10)

		Assertions.assertTrue(page.content.size > 0)
	}

	@Test
	fun `should throw exception for non-existing user`() {
		val exception = Assertions.assertThrows(ApiException::class.java) { userService.getByEmail("nonexistent@gmail.com") }

		assert(exception.message!!.contains("User not found"))
	}
}
