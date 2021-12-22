package com.github.welblade.workoutapp.presentation.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.welblade.workoutapp.presentation.ui.main.MainActivity
import com.github.welblade.workoutapp.core.extension.createDialog
import com.github.welblade.workoutapp.core.extension.createProgressDialog
import com.github.welblade.workoutapp.core.extension.email
import com.github.welblade.workoutapp.data.LoginRequest
import com.github.welblade.workoutapp.databinding.ActivityLoginBinding
import com.github.welblade.workoutapp.presentation.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import io.konform.validation.*
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val loginViewModel by viewModel<LoginViewModel>()
    private val progress by lazy { createProgressDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setViewModelObserver()
        setListeners()
    }

    private fun setListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            val loginRequest = LoginRequest(email, password)
            val validationResult = validateLogin(loginRequest)
            if(validationResult is Valid) {
                loginViewModel.login(loginRequest)
            } else {
                handleValidationErrors(validationResult.errors)
            }
        }
        binding.btnSingup.setOnClickListener { startRegisterActivity() }
    }

    private fun validateLogin(login: LoginRequest): ValidationResult<LoginRequest> {
        val validator = Validation<LoginRequest>{
            LoginRequest::email {
                minLength(4)
                maxLength(256)
                email()
            }
            LoginRequest::password{
                minLength(6)
                maxLength(16)
            }
        }
        return validator.invoke(login)
    }

    private fun setViewModelObserver() {
        loginViewModel.state.observe(this){
            when(it){
                LoginViewModel.State.Loading -> progress.show()
                is LoginViewModel.State.Error -> {
                    progress.dismiss()
                    handleViewModelErrors(it.error)
                }
                is LoginViewModel.State.Success -> {
                    progress.dismiss()
                    startMainActivity()
                }
            }
        }
    }

    private fun handleValidationErrors(errors: ValidationErrors) {
        binding.tilEmail.error = ""
        binding.tilPassword.error = ""
        errors.find { it.dataPath == ".email" }?.let {
            binding.tilEmail.error = it.message
        }
        errors.find { it.dataPath == ".password" }?.let {
            binding.tilPassword.error = it.message
        }
    }

    private fun handleViewModelErrors(error: Throwable) {
        when(error){
            is FirebaseAuthInvalidUserException -> {
                binding.tilEmail.error =
                    "user account corresponding to email does not exist or has been disabled."
            }
            is FirebaseAuthInvalidCredentialsException -> {
                when(error.errorCode){
                     "ERROR_INVALID_EMAIL" -> {
                         binding.tilEmail.error = error.localizedMessage
                     }
                    else -> {
                        binding.tilPassword.error = error.localizedMessage
                    }
                }
            }
            else -> {
                createDialog {
                    setMessage(error.localizedMessage)
                }.show()
                error.printStackTrace()
            }
        }
    }

    private fun startMainActivity(){
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }
    private fun startRegisterActivity(){
        Intent(this, RegisterActivity::class.java).also {
            startActivity(it)
        }
    }
}