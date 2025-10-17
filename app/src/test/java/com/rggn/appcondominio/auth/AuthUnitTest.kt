package com.rggn.appcondominio.auth

import org.junit.Test
import org.junit.Assert.*

class AuthUnitTest {

    @Test
    fun login_withValidCredentials_shouldReturnTrue() {
        // Arrange
        val user = "morador@teste.com"
        val password = "123"
        val authService = AuthService()

        // Act
        val result = authService.authenticateUser(user, password)

        // Assert
        assertTrue(result)
    }

    @Test
    fun login_withInvalidCredentials_shouldReturnFalse() {
        // Arrange
        val user = "visitante@teste.com"
        val password = "senhaerrada"
        val authService = AuthService()

        // Act
        val result = authService.authenticateUser(user, password)

        // Assert
        assertFalse(result)
    }
}