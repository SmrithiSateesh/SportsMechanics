package com.example.smrithi.sportsmechanics.network

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class RetrofitInstance {
    var retrofit: Retrofit? = null
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