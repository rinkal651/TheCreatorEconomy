package com.example.thecreatoreconomy.api

import com.example.thecreatoreconomy.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface TheCreatorEconomyAPI {

    @GET("dashboardNew")
    suspend fun getDashBoardData() : Response<ApiResponse>
}