package com.capstone.nutritrack.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.capstone.nutritrack.R
import com.capstone.nutritrack.ViewModelFactory
import com.capstone.nutritrack.customview.MyEditText
import com.capstone.nutritrack.customview.MyEmailEditText
import com.capstone.nutritrack.data.ResultState
import com.capstone.nutritrack.data.pref.UserModel
import com.capstone.nutritrack.databinding.ActivityLoginBinding
import com.capstone.nutritrack.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var myEditTextLogin: MyEditText
    private lateinit var myEmailEditTextLogin: MyEmailEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        myEditTextLogin = findViewById(R.id.passwordEditText)
        myEmailEditTextLogin = findViewById(R.id.emailEditText)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.login(email, password).observe(this, Observer { result ->
                when (result) {
                    is ResultState.Loading -> {
                        // Tampilkan loading
                    }
                    is ResultState.Success -> {
                        // Simpan sesi pengguna jika login berhasil
                        val userId = result.data.userId
                        val user = UserModel(userId, email, password, isLogin = true)
                        viewModel.saveSession(user)

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is ResultState.Error -> {
                        val errorMessage = result.error
                        AlertDialog.Builder(this).apply {
                            setTitle("Login Failed")
                            setMessage(errorMessage ?: "Unknown error occurred")
                            setPositiveButton("OK", null)
                            create()
                            show()
                        }
                    }
                }
            })
        }
    }
}
