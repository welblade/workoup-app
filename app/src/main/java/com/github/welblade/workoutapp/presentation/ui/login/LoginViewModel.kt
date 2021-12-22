package com.github.welblade.workoutapp.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.welblade.workoutapp.data.LoginRequest
import com.github.welblade.workoutapp.data.model.LoggedUser
import com.github.welblade.workoutapp.domain.LogInUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LogInUseCase
): ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun login(loginRequest: LoginRequest){
        viewModelScope.launch{
            loginUseCase(loginRequest)
                .flowOn(Dispatchers.Main)
                .onStart { _state.value = State.Loading }
                .catch   {
                    _state.value = State.Error(it)
                }
                .collect { _state.value = State.Success(it) }
        }
    }

    sealed class State {
        object Loading: State()
        data class Success(val user: LoggedUser): State()
        data class Error(val error: Throwable): State()
    }
}