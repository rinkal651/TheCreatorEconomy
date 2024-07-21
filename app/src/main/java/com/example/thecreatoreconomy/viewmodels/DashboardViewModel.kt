package com.example.thecreatoreconomy.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecreatoreconomy.models.ApiResponse
import com.example.thecreatoreconomy.repository.TheCreatorEconomyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: TheCreatorEconomyRepository) : ViewModel() {
    val apiData: StateFlow<ApiResponse?>
        get() = repository.data
    init {
        viewModelScope.launch {
            repository.getDashBoardData()
        }
    }
}