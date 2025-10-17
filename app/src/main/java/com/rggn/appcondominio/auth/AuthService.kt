package com.rggn.appcondominio.auth

class AuthService {
    fun authenticateUser(user: String, password: String): Boolean {
        return user == "morador@teste.com" && password == "123"
    }
}