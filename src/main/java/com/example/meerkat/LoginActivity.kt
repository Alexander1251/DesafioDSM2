package com.example.meerkat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_login)
            Log.d("LoginActivity", "Layout asignado correctamente.")

            auth = FirebaseAuth.getInstance()

            etEmail = findViewById(R.id.etEmail)
            etPassword = findViewById(R.id.etPassword)
            btnLogin = findViewById(R.id.btnLogin)
            btnRegister = findViewById(R.id.btnRegister)

            Log.d("LoginActivity", "Vistas inicializadas.")

            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, getString(R.string.invalid_fields), Toast.LENGTH_SHORT).show()
                }
            }

            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, getString(R.string.registration_success), Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, getString(R.string.invalid_fields), Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("LoginActivity", "Error en onCreate", e)
            Toast.makeText(this, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }
}
