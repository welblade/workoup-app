package com.github.welblade.workoutapp.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.welblade.workoutapp.core.extension.createDialog
import com.github.welblade.workoutapp.core.extension.createProgressDialog
import com.github.welblade.workoutapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val itemAdapter by lazy { RoutineItemAdapter() }
    private val mainViewModel: MainViewModel by viewModel()
    private val progress by lazy { createProgressDialog() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRecyclerView()
        setViewModelObserver()
    }

    private fun setRecyclerView() {
        binding.rvRoutine.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = itemAdapter
        }
    }

    private fun setViewModelObserver() {
        mainViewModel.state.observe(this) {
            when(it){
                MainViewModel.State.Loading -> { progress.show() }
                is MainViewModel.State.Error -> {
                    progress.dismiss()
                    createDialog {
                        setMessage(it.error.localizedMessage)
                    }.show()
                }
                is MainViewModel.State.Success -> {
                    itemAdapter.submitList(it.routines)
                }
            }
        }
    }
}