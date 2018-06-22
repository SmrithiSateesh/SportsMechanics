package com.example.basavaraj.sportsmechanics.network

import com.example.basavaraj.sportsmechanics.model.SearchList
import com.example.basavaraj.sportsmechanics.model.SearchRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call


interface GetSearchDataService {

   // @POST("5b20997930000088005c70d6")
    @POST("api/video_searches/search.json")
    fun createUser(@Body searchRequest: SearchRequest): Call<SearchList>
}