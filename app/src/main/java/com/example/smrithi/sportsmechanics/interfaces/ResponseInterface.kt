package com.example.smrithi.sportsmechanics.interfaces

import com.example.smrithi.sportsmechanics.model.SearchList
import retrofit2.Response

interface ResponseInterface {

    fun onSuccess(response: Response<SearchList>?, firstQuery: Boolean)
    fun onFailure(t: Throwable?, firstQuery: Boolean)
}