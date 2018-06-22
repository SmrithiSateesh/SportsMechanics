package com.example.basavaraj.sportsmechanics.network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class RetrofitInstance {
    var retrofit: Retrofit? = null
   // private val BASE_URL = "http://www.mocky.io/v2/"
    private val BASE_URL = "https://sportsmechanics.herokuapp.com/"

    fun getRetrofitInstance(): Retrofit {
        if (retrofit == null) {
            retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }
}