package com.example.smrithi.sportsmechanics.network

import com.example.smrithi.sportsmechanics.model.SearchList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSearchDataService {

    @GET("api/search.json")
    fun createSearchResquest(@Query("key") keyValue : String,
                             @Query("page") page : Int): Call<SearchList>
}