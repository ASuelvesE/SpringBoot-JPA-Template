package com.angel.api.configs

import com.angel.api.exceptions.ApiException
import com.angel.api.services.impl.UserServiceImpl
import com.angel.api.utils.Utils
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

@Component
class JwtAuthenticationFilter(
    private val userService: UserServiceImpl
) : OncePerRequestFilter() {

    @Throws(ServletException::class, java.io.IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        var email: String? = null
        var jwt: String? = null

        // Comprueba si el header tiene el formato correcto
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7)
            try {
                email = Utils.getValueFromToken("email", jwt)
            } catch (e: SecurityException) {
                throw ApiException(HttpStatus.UNAUTHORIZED,"80/401",e.message)
            } catch (e: ExpiredJwtException) {
                throw ApiException(HttpStatus.UNAUTHORIZED,"80/401",e.message)
            }
        }

        if (email != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userService.loadUserByUsername(email)

            // Verifica el token
            if (Utils.validateToken(jwt!!)) {
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        filterChain.doFilter(request, response)
    }
}
