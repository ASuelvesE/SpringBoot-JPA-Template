package com.angel.api.utils

import com.angel.api.models.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.Date
import java.util.HashMap
import javax.crypto.spec.SecretKeySpec

object Utils {
    private val passwordEncoder = BCryptPasswordEncoder()
    private const val SECRET_KEY = "ej7NrahZu5C5wtlbp9OuLOha7GEh5MxiC0RyBwSosrb5kfS/hd5sqsIwBp9568PcRPKzVzlB/7I7wAyJP605Dw=="
    private const val EXPIRATION_TIME = 1000 * 60 * 60 * 10 // 10 horas

    fun encodePassword(rawPassword: String): String {
        return passwordEncoder.encode(rawPassword)
    }

    fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }

    fun generateToken(user: User): String {
        val claims: MutableMap<String, Any> = HashMap()
        claims["email"] = user.email
        claims["name"] = user.name
        claims["surnames"] = user.surnames
        claims["role"] = user.role
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims: Claims = Jwts.parserBuilder()
                .setSigningKey(this.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .body
            claims.expiration.after(Date())
        } catch (e: Exception) {
            false // Si hay un error, el token no es válido
        }
    }

    private fun getSigningKey(): SecretKeySpec {
        val keyBytes = SECRET_KEY.toByteArray()
        return SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.jcaName)
    }

    fun getValueFromToken(key: String, token: String): String? {
        val claims: Claims = Jwts.parserBuilder()
            .setSigningKey(this.getSigningKey())
            .build()
            .parseClaimsJws(token)
            .body
        return claims[key]?.toString()
    }
}
