package com.github.welblade.workoutapp.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.welblade.workoutapp.data.model.Routine
import com.github.welblade.workoutapp.domain.ListRoutinesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val listRoutines: ListRoutinesUseCase
): ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun getRoutineList(){
        viewModelScope.launch{
            listRoutines()
                .flowOn(Dispatchers.Main)
                .onStart { _state.value = State.Loading }
                .catch { _state.value = State.Error(it) }
                .collect{_state.value = State.Success(it) }
        }
    }

    sealed class State {
        object Loading: State()
        data class Success(val routines: List<Routine>): State()
        data class Error(val error: Throwable): State()
    }
}