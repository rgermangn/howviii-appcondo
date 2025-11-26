package com.rggn.appcondominio.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rggn.appcondominio.R
import com.rggn.appcondominio.databinding.ActivityLoginBinding
import com.rggn.appcondominio.home.DashboardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authService = AuthService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Oculta a mensagem de status assim que o usuário começa a digitar
                binding.statusMessage.visibility = View.GONE
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                updateLoginButtonState()
            }
        }

        binding.emailEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (authService.authenticateUser(email, password)) {
                binding.statusMessage.visibility = View.GONE
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.statusMessage.text = getString(R.string.invalid_credentials)
                binding.statusMessage.visibility = View.VISIBLE
            }
        }

        updateLoginButtonState()
    }
    private fun updateLoginButtonState() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        
        val isEnabled = email.isNotEmpty() && password.isNotEmpty()
        binding.loginButton.isEnabled = isEnabled
    }
}
