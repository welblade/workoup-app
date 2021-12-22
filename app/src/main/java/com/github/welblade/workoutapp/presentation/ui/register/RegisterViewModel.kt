package com.github.welblade.workoutapp.presentation.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.welblade.workoutapp.data.RegisterRequest
import com.github.welblade.workoutapp.data.model.LoggedUser
import com.github.welblade.workoutapp.domain.RegisterUserUseCase
import com.github.welblade.workoutapp.presentation.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel(){
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun registerUser(registerRequest: RegisterRequest){
        viewModelScope.launch {
            registerUserUseCase(registerRequest)
                .flowOn(Dispatchers.Main)
                .onStart { _state.value = State.Loading }
                .catch   { _state.value = State.Error(it) }
                .collect { _state.value = State.Success(it) }
        }
    }

    sealed class State {
        object Loading: State()
        data class Success(val user: LoggedUser): State()
        data class Error(val error: Throwable): State()
    }
}