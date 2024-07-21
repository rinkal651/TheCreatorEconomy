package com.example.thecreatoreconomy.repository

import com.example.thecreatoreconomy.api.TheCreatorEconomyAPI
import com.example.thecreatoreconomy.models.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TheCreatorEconomyRepository @Inject constructor(private val theCreatorEconomyAPI: TheCreatorEconomyAPI) {


    private val _data = MutableStateFlow<ApiResponse?>(null)
    val data: StateFlow<ApiResponse?>
        get() = _data

    suspend fun getDashBoardData() {
        print("getDashBoardData")
        val result = theCreatorEconomyAPI.getDashBoardData()
        if (result.isSuccessful && result.body() != null) {
            print("getDashBoardData: ${result.body()}")
            _data.emit(result.body()!!)
        }
    }

}