package com.example.smrithi.sportsmechanics.network

import com.example.smrithi.sportsmechanics.model.SearchList
import com.example.smrithi.sportsmechanics.model.SearchPlayerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSearchDataService {

    @GET("api/search.json")
    fun createSearchResquest(@Query("key") keyValue: String,
                             @Query("batsman") batsman: String,
                             @Query("bowler") bowler: String,
                             @Query("fielder") fielder: String,
                             @Query("match_type[]") match_type_ODI: ArrayList<String>,
                             @Query("page") page: Int,
                             @Query("client_id") client_id: String): Call<SearchList>

    @GET("/api/search_player.json")
    fun searchPlayer(@Query("key")key : String,
                     @Query("field_name")field_name : String): Call<SearchPlayerResponse>
}