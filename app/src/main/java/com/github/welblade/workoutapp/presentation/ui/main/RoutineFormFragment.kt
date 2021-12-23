package com.github.welblade.workoutapp.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.welblade.workoutapp.core.extension.*
import com.github.welblade.workoutapp.data.model.Routine
import com.github.welblade.workoutapp.databinding.FragmentRoutineFormBinding
import com.google.firebase.Timestamp
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class RoutineFormFragment: Fragment() {
    private var routineFormBinding: FragmentRoutineFormBinding? = null
    private val routineFormViewModel by viewModel<RoutineFormViewModel>()
    private val progress by lazy { layoutInflater.context.createProgressDialog() }
    private var _selectedDate: Date = Date()
    var onDetachListener: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        routineFormBinding = FragmentRoutineFormBinding.inflate(inflater, container, false)
        return routineFormBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialState()
        setViewModelObserver()
        setListeners()
    }

    private fun setViewModelObserver() {
        routineFormViewModel.state.observe(viewLifecycleOwner){
            routineFormBinding!!.root.hideSoftKeyboard()
            when(it){
                RoutineFormViewModel.State.Loading -> progress.show()
                is RoutineFormViewModel.State.Error -> {
                    progress.dismiss()
                    layoutInflater.context.createDialog {
                        setMessage(it.error.localizedMessage)
                    }.show()
                }
                RoutineFormViewModel.State.Success -> {
                    progress.dismiss()
                    layoutInflater.context.createDialog {
                        setMessage("Treino agendado.")
                    }.show()
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun setInitialState() {
        routineFormBinding!!.calendarView.visibility = View.INVISIBLE
        val date = Date(routineFormBinding!!.calendarView.date)
        routineFormBinding!!.tvDate.text = date.format("dd 'de' MMMM 'de' YYYY")
    }

    private fun setListeners() {
        routineFormBinding!!.btnCalendar.setOnClickListener { _ ->
            routineFormBinding!!.root.hideSoftKeyboard()
            routineFormBinding!!.calendarView.visibility = View.VISIBLE
        }
        routineFormBinding!!.calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            _selectedDate = Date().of(year, month, dayOfMonth)
            routineFormBinding!!.tvDate.text = _selectedDate.format("dd 'de' MMMM 'de' YYYY")
            calendarView.visibility = View.INVISIBLE
        }
        routineFormBinding!!.btnSave.setOnClickListener {
            routineFormBinding!!.root.hideSoftKeyboard()
            val name = routineFormBinding!!.tilName.editText!!.text.toString()
            val description = routineFormBinding!!.tilDescription.editText!!.text.toString()
            val routine =  Routine(
                name = name,
                description = description,
                date = Timestamp(_selectedDate),
                exercises = listOf()
            )
            routineFormViewModel.saveRoutine(routine)
        }
    }

    override fun onDetach() {
        onDetachListener()
        super.onDetach()
    }
}