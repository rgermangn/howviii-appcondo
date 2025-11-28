package com.rggn.appcondominio.auth

import org.junit.Test
import org.junit.Assert.*

class AuthUnitTest {

    @Test
    fun login_withValidCredentials_shouldReturnTrue() {
        val user = "morador@teste.com"
        val password = "123"
        val authService = AuthService()

        val result = authService.authenticateUser(user, password)

        assertTrue(result)
    }

    @Test
    fun login_withInvalidCredentials_shouldReturnFalse() {
        val user = "visitante@teste.com"
        val password = "senhaerrada"
        val authService = AuthService()

        val result = authService.authenticateUser(user, password)

        assertFalse(result)
    }
}