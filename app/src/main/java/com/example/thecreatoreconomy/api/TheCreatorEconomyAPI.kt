package com.example.thecreatoreconomy.api

import com.example.thecreatoreconomy.models.ApiResponse
import com.example.thecreatoreconomy.models.TweetListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface TheCreatorEconomyAPI {

    @GET("dashboardNew")
    suspend fun getDashBoardData() : Response<ApiResponse>
}