package com.example.basavaraj.sportsmechanics.network

import com.example.smrithi.sportsmechanics.model.SearchList
import com.example.smrithi.sportsmechanics.model.SearchRequest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetSearchDataService {

   // @POST("5b20997930000088005c70d6")
  /*  @POST("api/video_searches/search.json")
    fun createUser(@Body searchRequest: SearchRequest): Call<SearchList>*/

    @GET("api/search.json")
    fun createSearchResquest(@Query ("key") keyValue : String,
                             @Query ("page") page : Int): Call<SearchList>
}