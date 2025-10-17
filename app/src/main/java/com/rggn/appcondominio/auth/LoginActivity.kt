package com.rggn.appcondominio.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rggn.appcondominio.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authService = AuthService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (authService.authenticateUser(email, password)) {
                binding.statusTextView.text = "Login bem-sucedido"
            } else {
                binding.statusTextView.text = "Credenciais inválidas"
            }
        }
    }
}
