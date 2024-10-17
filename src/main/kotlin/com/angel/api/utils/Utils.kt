package com.angel.api.utils

import com.angel.api.models.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.io.InputStream
import java.util.*
import javax.crypto.spec.SecretKeySpec


object Utils {
    private val properties: Properties = loadProperties()
    private val passwordEncoder = BCryptPasswordEncoder()

    private var SECRET_KEY: String = this.getJwtSecret()

    private const val EXPIRATION_TIME = 1000 * 60 * 60 * 10 // 10 horas

    private fun loadProperties(): Properties {
        val prop = Properties()
        try {
            val inputStream: InputStream? = javaClass.classLoader.getResourceAsStream("application.properties")
            prop.load(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return prop
    }
    fun getJwtSecret(): String {
        val secret = properties.getProperty("jwt.secret", "")
        return secret.trim()
    }

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
            .signWith(Keys.hmacShaKeyFor(SECRET_KEY.toByteArray(Charsets.UTF_8)), SignatureAlgorithm.HS256) // Usar la clave generada
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
        val keyBytes = Base64.getDecoder().decode(SECRET_KEY) // Decodifica la clave secreta
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
