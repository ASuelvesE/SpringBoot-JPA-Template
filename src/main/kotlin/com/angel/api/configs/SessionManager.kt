package com.angel.api.configs

import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope

@RequestScope
@Component
class SessionManager {

    private var token: String? = null

    fun saveToken(token: String) {
        this.token = token
    }

    fun getToken(): String? {
        return token
    }

    fun clearToken() {
        token = null
    }
}
