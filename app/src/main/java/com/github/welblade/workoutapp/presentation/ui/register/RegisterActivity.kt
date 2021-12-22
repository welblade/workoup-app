package com.github.welblade.workoutapp.presentation.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.welblade.workoutapp.core.extension.createDialog
import com.github.welblade.workoutapp.core.extension.createProgressDialog
import com.github.welblade.workoutapp.core.extension.email
import com.github.welblade.workoutapp.data.RegisterRequest
import com.github.welblade.workoutapp.databinding.ActivityRegisterBinding
import com.github.welblade.workoutapp.presentation.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import io.konform.validation.Valid
import io.konform.validation.Validation
import io.konform.validation.ValidationErrors
import io.konform.validation.ValidationResult
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }
    private val progress by lazy { createProgressDialog() }
    private val registerViewModel by viewModel<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setViewModelObserver()
        setListeners()
    }

    private fun setListeners() {
        binding.btnRegister.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            val name = binding.tilName.editText?.text.toString()

            val registerRequest = RegisterRequest(email, password, name)
            val validationResult = validateRegister(registerRequest)
            if(validationResult is Valid){
                registerViewModel.registerUser(registerRequest)
            } else {
                handleValidationErrors(validationResult.errors)
            }
        }
        binding.btnLogin.setOnClickListener { startLoginActivity() }
    }

    private fun validateRegister(request: RegisterRequest): ValidationResult<RegisterRequest> {
        val validator = Validation<RegisterRequest>{
            RegisterRequest::email {
                minLength(4)
                maxLength(256)
                email()
            }
            RegisterRequest::password{
                minLength(6)
                maxLength(16)
            }
            RegisterRequest::name {
                minLength(3)
                maxLength(32)
            }
        }
        return validator.invoke(request)
    }

    private fun setViewModelObserver() {
        registerViewModel.state.observe(this){
            when(it){
                RegisterViewModel.State.Loading -> progress.show()
                is RegisterViewModel.State.Error -> {
                    progress.dismiss()
                    handleViewModelError(it.error)
                }
                is RegisterViewModel.State.Success -> {
                    progress.dismiss()
                    showSuccessMessage()
                    startLoginActivity()
                }
            }
        }
    }

    private fun handleValidationErrors(errors: ValidationErrors) {
        binding.tilEmail.error = ""
        binding.tilPassword.error = ""
        binding.tilName.error = ""
        errors.find { it.dataPath == ".email" }?.let {
            binding.tilEmail.error = it.message
        }
        errors.find { it.dataPath == ".password" }?.let {
            binding.tilPassword.error = it.message
        }
        errors.find { it.dataPath == ".name" }?.let {
            binding.tilName.error = it.message
        }
    }

    private fun startLoginActivity() {
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun showSuccessMessage() {
        val successMessage = "Account successfully created."
        createDialog {
            setMessage(successMessage)
        }.show()
    }

    private fun handleViewModelError(error: Throwable) {
        when(error){
            is FirebaseAuthWeakPasswordException -> {
                binding.tilPassword.error = "Weak password."
            }
            is FirebaseAuthInvalidCredentialsException -> {
                binding.tilEmail.error = "Email address is malformed."
            }
            is FirebaseAuthUserCollisionException -> {
                binding.tilEmail.error = "Already exists an account with the given email address."
            }
            else -> {
                createDialog {
                    setMessage(error.message)
                }.show()
            }
        }
    }
}