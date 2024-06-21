package com.capstone.nutritrack.ui.signup

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
import com.capstone.nutritrack.databinding.ActivitySignupBinding
import com.capstone.nutritrack.ui.login.LoginActivity
import com.capstone.nutritrack.ui.main.MainActivity

class SignUpActivity : AppCompatActivity() {

    private val viewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivitySignupBinding
    private lateinit var myEditTextSignUp: MyEditText
    private lateinit var myEmailEditTextSignUp: MyEmailEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        myEditTextSignUp = findViewById(R.id.passwordEditText)
        myEmailEditTextSignUp = findViewById(R.id.emailEditText)
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
        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val fullName = binding.nameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.register(fullName, email, password).observe(this, Observer { result ->
                when (result) {
                    is ResultState.Loading -> {
                        // Tampilkan loading
                    }
                    is ResultState.Success -> {
                        AlertDialog.Builder(this).apply {
                            setTitle("Success")
                            setMessage(result.data.message)
                            setPositiveButton("OK") { _, _ ->
                                // Navigate to login screen after successful signup
                                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                    is ResultState.Error -> {
                        val errorMessage = result.error
                        AlertDialog.Builder(this).apply {
                            setTitle("Sign Up Failed")
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
